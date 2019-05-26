package br.com.jmoicano.popularmovies.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import org.joda.time.LocalDate;

import br.com.jmoicano.popularmovies.R;

import static br.com.jmoicano.popularmovies.services.Constants.IMAGE_URL;

public final class BindingAdapters {

    private BindingAdapters(){
        throw new IllegalStateException("Utilitary class");
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(IMAGE_URL + imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(view);
    }

    @BindingAdapter({"parseReleaseYear"})
    public static void parseReleaseYear(TextView view, LocalDate releaseDate) {
        String year = releaseDate.year().getAsString();
        view.setText(year);
    }

    @BindingAdapter({"rating"})
    public  static void rating(TextView view, String rating) {
        view.setText(String.format("%s/10", rating));
    }
}
