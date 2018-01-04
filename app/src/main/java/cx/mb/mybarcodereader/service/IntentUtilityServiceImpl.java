package cx.mb.mybarcodereader.service;

import android.content.ClipData;
import android.content.Intent;

/**
 * /**
 * Create intent utility.
 * Created by toshiaki on 2018/01/04.
 */

public class IntentUtilityServiceImpl implements IntentUtilityService {

    @Override
    public Intent createTextCopyIntent(String text) {

        ClipData.Item clip = new ClipData.Item(text);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, clip.getText());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }
}
