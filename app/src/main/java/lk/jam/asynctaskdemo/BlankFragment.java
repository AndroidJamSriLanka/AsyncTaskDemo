package lk.jam.asynctaskdemo;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private ProgressDialog dialog;

    private boolean isTaskStarted = false;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        if(isTaskStarted) {
            dialog = ProgressDialog.show(getActivity(), "loading", "loading");
        } else {
            FetchDataTask task = new FetchDataTask();
            task.execute("Hello", " Jammers");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isTaskStarted) {
            dialog = ProgressDialog.show(getActivity(), "Loading", "Doing task");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private class FetchDataTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(), "Starting to do something bad", Toast.LENGTH_SHORT).show();
            dialog = ProgressDialog.show(getActivity(), "Loading", "Doing task");
            isTaskStarted = true;

        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            for (int i = 0; i < 15; i++) {
                result = Utilities.doSomethingHeavy(params[0], params[1]);
                publishProgress(i);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView output = (TextView) getActivity().findViewById(R.id.content);
            output.setText(s);
            isTaskStarted = false;
            dialog.dismiss();

            Toast.makeText(getActivity(), "I finished doing it", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setMessage(values[0] + " times");

        }
    }


}
