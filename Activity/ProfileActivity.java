package com.example.smartbin.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartbin.R;
import com.example.smartbin.databinding.ActivityProfileBinding;
import com.example.smartbin.model.ModelUser;
import com.example.smartbin.retrofit.ServiceURL;
import com.example.smartbin.utils.AppUtils;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class ProfileActivity extends AppCompatActivity {
    ModelUser.LoginAuth user = AppUtils.getUserModel();
    ActivityProfileBinding binding;
    Dialog dialogRewardShow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ModelUser.LoginAuth user = AppUtils.getUserModel();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        binding.userEmail.setText(user.getEmail());
        binding.username.setText(user.getName());

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        binding.viewRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewards();
            }
        });
        binding.userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userGuide();
            }
        });
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        binding.appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutus();
            }
        });

    }

    private void logout() {
        Prefs.clear();
        AppUtils.callActivityWithClearTask(this, LoginActivity.class);
    }

    private void aboutus() {
        Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
        startActivity(intent);
    }

    private void userGuide() {
        Intent intent = new Intent(getApplicationContext(),UserGuideActivity.class);
        startActivity(intent);
    }

    private void showRewards() {
        /*Intent intent = new Intent(getApplicationContext(),RewardActivty.class);
        startActivity(intent);*/
        dialogRewardShow = new Dialog(this);
        dialogRewardShow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialogRewardShow.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogRewardShow.setContentView(R.layout.dialog_show_reward);
        dialogRewardShow.setCancelable(true);
        Button xBtn = dialogRewardShow.findViewById(R.id.OkBtn);
        TextView xTvReward = dialogRewardShow.findViewById(R.id.totalRewards);
        xTvReward.setText(user.getRewards()+" Points you earned");
        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRewardShow.dismiss();
            }
        });
        dialogRewardShow.show();
    }

    private void editProfile() {
        Intent intent = new Intent(getApplicationContext(),ProfileEditActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        ImageView profileImg = binding.profileDP;
        super.onResume();
        Glide.with(this)
                .load(ServiceURL.imageurl + user.getProfileImage())
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_profile_primary)
                .into(profileImg);
    }
}
