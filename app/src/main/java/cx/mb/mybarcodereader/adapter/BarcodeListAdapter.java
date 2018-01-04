package cx.mb.mybarcodereader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gfx.android.orma.Relation;
import com.github.gfx.android.orma.widget.OrmaListAdapter;

import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.orma.Barcode;

/**
 * List adapter of barcode list.
 * Created by toshiaki on 2017/12/30.
 */
public class BarcodeListAdapter extends OrmaListAdapter<Barcode> {

    /**
     * Constructor.
     *
     * @param context  context.
     * @param relation relation.
     */
    public BarcodeListAdapter(@NonNull Context context, @NonNull Relation<Barcode, ?> relation) {
        super(context, relation);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_result, viewGroup, false);
            holder = new ViewHolder();
            holder.type = view.findViewById(R.id.main_result_item_type);
            holder.createAt = view.findViewById(R.id.main_result_item_create_at);
            holder.text = view.findViewById(R.id.main_result_item_text);
            holder.image = view.findViewById(R.id.main_result_item_image);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Barcode item = getItem(i);
        String formattedCreateAt = DateFormat.format("yyyy/MM/dd kk:mm:ss", item.createAt).toString();

        holder.type.setText(item.type);
        holder.text.setText(item.text);
        holder.createAt.setText(formattedCreateAt);
        if (item.bitmap == null) {
            final Bitmap bitmap = BitmapFactory.decodeByteArray(item.image, 0, item.image.length);
            item.bitmap = bitmap;
        }
        holder.image.setImageBitmap(item.bitmap);

        return view;
    }

    /**
     * View holder.
     */
    private class ViewHolder {
        /**
         * Type string.
         */
        TextView type;

        /**
         * Create at.
         */
        TextView createAt;

        /**
         * text string.
         */
        TextView text;

        /**
         * Scanned image.
         */
        ImageView image;
    }
}

