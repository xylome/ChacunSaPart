package placard.fr.eu.chacunsapart.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.Group;
import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.Participation;
import placard.fr.eu.org.chacunsaparttesteur.R;

/**
 * Created by xylome on 24/10/2015.
 */
public class ParticipationAdapter extends BaseAdapter {

    private static final String TAG = ParticipationAdapter.class.getSimpleName();
    private Context mContext;
    private Group mGroup;
    private ArrayList<Participation> mParticipations;


    public ParticipationAdapter(Context c, Group group, ArrayList<Participation> participations) {
        mGroup = group;
        mParticipations = participations;
        mContext = c;
    }

    public ArrayList<Participation> getParticipations() {
        return mParticipations;
    }

    @Override
    public int getCount() {
        return mParticipations.size();
    }

    @Override
    public Object getItem(int i) {
        return mParticipations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) li.inflate(R.layout.item_participation, null);

            ViewHolder vh = new ViewHolder();
            vh.parts_lv = (LinearLayout) view.findViewById(R.id.item_participation_parts);
            vh.actor = (TextView) view.findViewById(R.id.item_participation_actor);
            vh.whole_parts = (TextView) view.findViewById(R.id.item_participation_whole_parts);
            vh.decim_parts = (TextView) view.findViewById(R.id.item_participation_decim_parts);
            vh.plusBtn = (Button) view.findViewById(R.id.item_participation_plus_button);
            vh.minusBtn = (Button) view.findViewById(R.id.item_participation_minus_button);

            view.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) view.getTag();

        final LinearLayout parts_lv_copy = vh.parts_lv;
        final TextView whole_parts_copy = vh.whole_parts;
        final TextView decim_parts_copy = vh.decim_parts;

        final Participation p = (Participation) getItem(i);

        View.OnClickListener buttonClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(view.getId()) {
                    case R.id.item_participation_plus_button:
                        p.addPart();
                        break;
                    case R.id.item_participation_minus_button:
                        p.subPart();
                        break;
                }

                if (p.displayDecimParts(mGroup.getFraction(),false).equals("")) {
                    decim_parts_copy.setVisibility(View.GONE);
                } else {
                    decim_parts_copy.setVisibility(View.VISIBLE);
                }

                whole_parts_copy.setText(p.displayWholeParts(mGroup.getFraction()));
                decim_parts_copy.setText(Html.fromHtml(p.displayDecimParts(mGroup.getFraction(),true)));


                if(p.getParts() == 0) {
                    parts_lv_copy.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_equals_0));
                } else {
                    parts_lv_copy.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_supp_0));
                }
            }
        };

        vh.plusBtn.setOnClickListener(buttonClick);
        vh.minusBtn.setOnClickListener(buttonClick);

        vh.actor.setText(p.getGuestNick());
        vh.whole_parts.setText(p.displayWholeParts(mGroup.getFraction()));
        vh.decim_parts.setText(Html.fromHtml(p.displayDecimParts(mGroup.getFraction(), true)));

        if (p.displayDecimParts(mGroup.getFraction(),false).equals("")) {
            vh.decim_parts.setVisibility(View.GONE);
        } else {
            vh.decim_parts.setVisibility(View.VISIBLE);
        }

        if(p.getParts() == 0) {
            vh.parts_lv.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_equals_0));
        } else {
            vh.parts_lv.setBackground(mContext.getResources().getDrawable(R.drawable.nb_parts_supp_0));
        }


        return view;
    }

    static class ViewHolder {
        public LinearLayout parts_lv;
        public TextView actor;
        public TextView whole_parts;
        public TextView decim_parts;
        public Button plusBtn;
        public Button minusBtn;
    }

}