package view;

import org.json.JSONObject;

public sealed interface CEPSearchObserver permits CEPSearchView {

    void search(JSONObject response);

}
