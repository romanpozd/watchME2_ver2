package com.watchme.roman.watchme_ver2.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.watchme.roman.watchme_ver2.Model.Actors;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by roman on 07/09/2015.
 */
public class ActorDetailsFragment extends Fragment {

    String id;

    // Views
    TextView actor_name,birthday,birthplace,biography;

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra(Constants.ID_TAG);

        try {
            UpdateUI();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actor_details_fragment, container, false);

        actor_name = (TextView)view.findViewById(R.id.tv_actor_details_name);
        birthday = (TextView)view.findViewById(R.id.tv_actor_birthday);
        birthplace = (TextView)view.findViewById(R.id.tv_actor_birthplace);
        biography = (TextView)view.findViewById(R.id.tv_actor_biography);

        return view;
    }
    private void UpdateUI() throws UnsupportedEncodingException, JSONException {
        Uri actorDetails = Utility.buildUriForPerson(getActivity(),id);
        ParseJSON parseJSON = new ParseJSON();
        parseJSON.ParseActorDetails(actorDetails, new ParseJSON.VolleyCallBackActorDetails() {
            @Override
            public void onSuccess(Actors actor) {
                actor_name.setText(actor.getActorName());
                if (actor.getBirthday().equals("null") || actor.getBirthday().equals(""))
                    birthday.setText("Birthday: no details");
                else{
                    String temp = actor.getBirthday().replace("-","/");
                    birthday.setText("Birthday: " + temp);
                }

                if (actor.getBirthPlace().equals("null") || actor.getBirthPlace().equals(""))
                    birthplace.setText("Birthplace: no details");
                else
                    birthplace.setText("Birthplace: " + actor.getBirthPlace());
                if (actor.getBiography().equals("null") || actor.getBiography().equals(""))
                    biography.setText("no details");
                else
                    biography.setText(actor.getBiography());
            }
        });
    }
}
