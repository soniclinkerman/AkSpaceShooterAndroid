package com.ak.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class LevelSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        RecyclerView levelGrid = findViewById(R.id.levelGrid);
        LevelSelectAdapter adapter = new LevelSelectAdapter(this);
        levelGrid.setAdapter(adapter);
        levelGrid.setLayoutManager(new GridLayoutManager(this,4));
        //levelGrid.setLayoutManager(new GridLayoutManager(this,4, GridLayoutManager.HORIZONTAL, false));
    }






    public class LevelSelectAdapter extends RecyclerView.Adapter<LevelSelectAdapter.LevelViewHolder> {
        private final LayoutInflater layoutInflater;
        private final List<String> levels = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20");
        LevelSelectAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.level_item,parent,false);
            return new LevelViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
            String currentLevel = levels.get(position);
            holder.levelNumber=currentLevel;
            holder.levelView.setText(currentLevel);
        }

        @Override
        public int getItemCount() {
            if(levels!=null)
                return levels.size();
            else return 0;
        }




        class LevelViewHolder extends RecyclerView.ViewHolder{
            private final TextView levelView;

            private String levelNumber;

            LevelViewHolder(View itemView) {
                super(itemView);
                levelView = itemView.findViewById(R.id.levelTextView);

                itemView.setOnClickListener(view ->{
                    Intent intent = new Intent(LevelSelectActivity.this, AndroidLauncher.class);
                    intent.putExtra("level number", levelNumber);
                    startActivity(intent);
                });
            }
        }
    }

}
