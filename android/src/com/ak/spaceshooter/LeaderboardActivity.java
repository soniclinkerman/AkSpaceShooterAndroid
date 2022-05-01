package com.ak.spaceshooter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.spaceshooter.db.LevelDatabase;
import com.google.android.gms.tasks.Task;
import com.google.api.Authentication;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderboardActivity extends AppCompatActivity {
    private static final String TAG = "LeaderboardActivity";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    LeaderboardActivity.LeaderBoardAdapter adapter;

    String firestoreUID;
    long userHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        RecyclerView levelGrid = findViewById(R.id.levelGrid);

        adapter = new LeaderboardActivity.LeaderBoardAdapter(this);
        levelGrid.setAdapter(adapter);
        levelGrid.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(levelGrid.getContext(),
                GridLayoutManager.VERTICAL);
        levelGrid.addItemDecoration(dividerItemDecoration);
        LevelDatabase.getDatabase(this).levelDAO().getAll().observe(this, (v)->{
            userHighScore=v.stream().map(level -> level.high_score).reduce(Math::max).get();
            auth.signInAnonymously().addOnCompleteListener(this::onAuthenticate);
        });

        findViewById(R.id.data_upload).setOnClickListener(this::uploadData);


    }
    public void uploadData(View v){
        DocumentReference docRef = db.collection("leaderboard").document("space shooter");
        docRef.get().addOnCompleteListener((task)->{
            if (!task.isSuccessful()) {
                Log.d(TAG, "get failed with ", task.getException());
                return;
            }
            DocumentSnapshot document = task.getResult();
            HashMap<String,HashMap> lb = new HashMap(document.getData());
            String leaderBoardName = PreferenceManager.getDefaultSharedPreferences(this).getString("leaderBoardName",firestoreUID);
            lb.put(firestoreUID, new LeaderboardEntry(leaderBoardName,userHighScore,false).toHashMap());
            db.collection("leaderboard").document("space shooter").set(lb);
            docRef.get().addOnCompleteListener(this::onDataRetrieved);
        });
    }


    public void onAuthenticate(@NonNull Task<AuthResult> task){
        if (!task.isSuccessful()) {
            Log.d(TAG, "authenticate failed with ", task.getException());
            return;
        }
        //Authentication succeeded
        firestoreUID = auth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("leaderboard").document("space shooter");
        docRef.get().addOnCompleteListener(this::onDataRetrieved);
    }


    public void onDataRetrieved(@NonNull Task<DocumentSnapshot> task) {
        if (!task.isSuccessful()) {
            Log.d(TAG, "get failed with ", task.getException());
            return;
        }
        DocumentSnapshot document = task.getResult();
        if (!document.exists()) {
            Log.d(TAG, "No such document");
            Map<String, Object> data = new HashMap<>();
            data.put("user2", new LeaderboardEntry("user2",100,false));
            data.put("user3", new LeaderboardEntry("user3",400,false));
            data.put("user4", new LeaderboardEntry("user4",200,false));
            data.put("user5", new LeaderboardEntry("user5",700,false));
            data.put("user6", new LeaderboardEntry("user6",300,false));
            data.put("user7", new LeaderboardEntry("user7",600,false));
            data.put("user8", new LeaderboardEntry("user8",800,false));
            data.put("user9", new LeaderboardEntry("user9",500,false));
            db.collection("leaderboard").document("space shooter").set(data);
            return;
        }
        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

        @SuppressWarnings("rawtypes")
        Map<String, Object> documentData =  document.getData();
        List<HashMap<String, Object>> leaderboard = new ArrayList(documentData.values());
        System.out.println(leaderboard);
        adapter.setLeaderboard(leaderboard);
        Toast.makeText(this, "Leaderboard data successfully loaded.", Toast.LENGTH_SHORT).show();
    }

    public class LeaderboardEntry{
        private String username;
        private long highScore;
        boolean isCurrentUser;

        public LeaderboardEntry(String username, long highScore, boolean isCurrentUser) {
            this.username = username;
            this.highScore = highScore;
            this.isCurrentUser = isCurrentUser;
        }

        public LeaderboardEntry(HashMap<String,Object> h) {
            this.username = (String) h.get("username");
            this.highScore = (long) h.get("highScore");
        }
        public String getUsername() {
            return username;
        }
        public long getHighScore() {
            return highScore;
        }
        public HashMap toHashMap(){
            HashMap h = new HashMap();
            h.put("username",username);
            h.put("highScore",highScore);
            return h;
        }
    }



    public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderboardViewHolder> {
        private final LayoutInflater layoutInflater;
        private List<LeaderboardEntry> leaderboard;
        LeaderBoardAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.leaderboard_item,parent,false);
            return new LeaderboardViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
            LeaderboardEntry e = leaderboard.get(position);
            holder.ranking.setText(String.valueOf(position+1));
            holder.username.setText(e.getUsername());
            holder.score.setText(String.valueOf(e.getHighScore()));
            if (e.isCurrentUser){
                holder.ranking.setTypeface(null, Typeface.BOLD);
                holder.username.setTypeface(null, Typeface.BOLD);
                holder.score.setTypeface(null, Typeface.BOLD);
            }
        }

        @Override
        public int getItemCount() {
            if(leaderboard !=null)
                return leaderboard.size();
            else return 0;
        }

        void setLeaderboard(List<HashMap<String, Object>> entries){
            leaderboard= entries.stream()
                    .map(LeaderboardEntry::new)
                    .collect(Collectors.toList());
            if(!leaderboard.stream().map(e-> e.username.equals(firestoreUID)).reduce((e, e2)->e||e2).get())
                leaderboard.add(new LeaderboardEntry("You",userHighScore,true));
            for (LeaderboardEntry entry : this.leaderboard){
                if(entry.username.equals(firestoreUID)){
                    entry.isCurrentUser=true;
                    entry.username="You";
                }
            }
            leaderboard.sort((e1, e2) -> (int) (e2.getHighScore() - e1.getHighScore()));
            notifyDataSetChanged();
        }





    class LeaderboardViewHolder extends RecyclerView.ViewHolder{
             TextView ranking;
             TextView username;
             TextView score;

            LeaderboardViewHolder(View itemView) {
                super(itemView);
                ranking = itemView.findViewById(R.id.ranking);
                username=itemView.findViewById(R.id.username);
                score=itemView.findViewById(R.id.high_score);

            }
        }
    }
}