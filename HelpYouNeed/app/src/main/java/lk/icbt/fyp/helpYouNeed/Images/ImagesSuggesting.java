package lk.icbt.fyp.helpYouNeed.Images;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import lk.icbt.fyp.helpYouNeed.R;

public class ImagesSuggesting extends AppCompatActivity {

    Animation Fade_in, Fade_out;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
        Fade_in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        Fade_out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);

        viewFlipper.setInAnimation(Fade_in);
        viewFlipper.setOutAnimation(Fade_out);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
    }
}
