package android.quiensoy;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultsAdapter extends BaseAdapter {
	 
    private Context context;
    private List<ResultItem> items;
 
    ResultsAdapter(Context context, List<ResultItem> items) {
        this.context = context;
        this.items = items;
    }
 
    @Override
    public int getCount() {
        return this.items.size();
    }
 
    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        View rowView = convertView;
 
        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                rowView = inflater.inflate(R.layout.resultlist_item, parent, false);
            }
        }
 
        // Set data into the view.
        TextView text = (TextView) rowView.findViewById(R.id.resultTextItem);
         
        ResultItem item = this.items.get(position);
        text.setText(item.getText());
        if (item.getIsCorrect())
        	text.setTextColor(Color.DKGRAY);
        else
        	text.setTextColor(Color.LTGRAY);
 
		//Change the font type
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/boys.ttf");
		text.setTypeface(typeFace);
		
        return rowView;
    }
 
}