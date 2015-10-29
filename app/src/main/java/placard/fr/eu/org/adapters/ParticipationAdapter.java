package placard.fr.eu.org.adapters;

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

import placard.fr.eu.org.chacunsapart.backend.beans.Expense;
import placard.fr.eu.org.chacunsapart.backend.beans.Group;
import placard.fr.eu.org.chacunsapart.backend.beans.Participation;
import placard.fr.eu.org.chacunsaparttesteur.R;

/**
 * Created by xylome on 24/10/2015.
 */
public class ParticipationAdapter extends BaseAdapter {

    private static final String TAG = ParticipationAdapter.class.getSimpleName();
    private Context mContext;
    private Group mGroup;
    private ArrayList<Participation> mParticipations;

    private ExpenseAdapter.ViewHolder mCurrentViewHolder;


    public ParticipationAdapter(Context c, Group group, ArrayList<Participation> participations) {
        mGroup = group;
        mParticipations = participations;
        mContext = c;
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

        final Participation p = (Participation) getItem(i);

        vh.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.addPart();
                // TODO add a final linear layout and update it.
                //vh = refresh(vh, p);
            }
        });

        vh = refresh(vh, p);

        return view;
    }

    public ParticipationAdapter.ViewHolder refresh(ParticipationAdapter.ViewHolder vh, Participation p) {

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


        return vh;
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