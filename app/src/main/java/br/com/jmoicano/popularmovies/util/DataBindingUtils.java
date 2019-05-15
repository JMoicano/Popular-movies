package br.com.jmoicano.popularmovies.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import br.com.jmoicano.popularmovies.R;

import static br.com.jmoicano.popularmovies.services.Constants.IMAGE_URL;

public final class DataBindingUtils {

    private DataBindingUtils(){
        throw new IllegalStateException("Utilitary class");
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(IMAGE_URL + imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(view);
    }
}
