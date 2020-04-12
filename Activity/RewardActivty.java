package com.example.smartbin.Activity;

import android.os.Bundle;
import android.widget.Toast;

import com.cooltechworks.views.ScratchTextView;
import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivityRewardActivtyBinding;
import com.example.smartbin.model.ModelBin;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.utils.AppUtils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class RewardActivty extends AppCompatActivity {
    public static ActivityRewardActivtyBinding bind;

    int rewards= 0;
    String bin_storage="0";
    ModelUser.LoginAuth user = AppUtils.getUserModel();
    private ArrayList<ModelBin.Bin> binList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_reward_activty);
        //Totoal Reward Display
        bind.rewardInfo.setText(user.getRewards()+" Points Rewards Earned");

        if (binList != null && binList.size() > 0) {
            for (int i = 0; i < binList.size(); i++) {
                ModelBin.Bin bin = binList.get(i);
                bin_storage = bin.getBinStorage();
                break;
            }
        }
        //ScratchCard
        ScratchTextView scratchTextView = new ScratchTextView(this);
        scratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView tv) {
                genReward();
                Toast.makeText(RewardActivty.this, "Revealed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {

            }
        });

    }
    public void genReward()
    {
        if (Integer.parseInt(bin_storage)>=0 && Integer.parseInt(bin_storage)<=200)
        {
            bind.sc.setText("Better Luck Next Time");
        }
    }
}
    /**/
    /*public static void onScracthCard()
    {
        bind.sc.setVisibility(View.VISIBLE);
    }*/

