package cx.mb.mybarcodereader.presentation.main;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.realm.RealmBarcode;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Result list adapter.
 */
public class ResultListAdapter extends RealmBaseAdapter<RealmBarcode> {

    /**
     * Constructor.
     * @param data data.
     */
    public ResultListAdapter(@Nullable OrderedRealmCollection<RealmBarcode> data) {
        super(data);
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

        final RealmBarcode item = getItem(i);
        assert item != null;

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
