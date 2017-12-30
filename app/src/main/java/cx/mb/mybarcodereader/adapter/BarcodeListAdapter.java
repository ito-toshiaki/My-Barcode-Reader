package cx.mb.mybarcodereader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gfx.android.orma.Relation;
import com.github.gfx.android.orma.widget.OrmaListAdapter;

import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.realm.Barcode;

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
            holder.text = view.findViewById(R.id.main_result_item_text);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Barcode item = getItem(i);

        holder.type.setText(item.getType());
        holder.text.setText(item.getText());
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
         * text string.
         */
        TextView text;
    }
}
