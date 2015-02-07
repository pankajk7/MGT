package com.mygrouptracker.mygrouptracker.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.activity.AppointmentSummary;
import com.mygrouptracker.mygrouptracker.activity.CalendarEvents;
import com.mygrouptracker.mygrouptracker.activity.EditAppointment;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.utility.MgtDateFormat;

public class AppointmentSummaryBaseAdapter extends BaseAdapter {

	AppointmentSummaryEntity objAppointmentSummaryEntity = new AppointmentSummaryEntity();
	List<AppointmentSummaryEntity> appointmentList;
	Button editButton;
	Context context;
	LayoutInflater inflater;

	public AppointmentSummaryBaseAdapter(Context context,
			List<AppointmentSummaryEntity> list) {
		this.context = context;
		appointmentList = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (appointmentList == null) {
			;
			return 0;
		}
		return appointmentList.size();
	}

	@Override
	public Object getItem(int position) {
		return appointmentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.appointment_summary_items,
					parent, false);
		}

		TextView date = (TextView) convertView
				.findViewById(R.id.textview_appt_summary_items_date);
		TextView notes = (TextView) convertView
				.findViewById(R.id.textview_appt_summary_items_notes);
		TextView recurring = (TextView) convertView
				.findViewById(R.id.textview_appt_summary_items_recurring);
		editButton = (Button) convertView
				.findViewById(R.id.btn_appointmentsummaryitems_edit);
		editButton.setTag(position);

		objAppointmentSummaryEntity = appointmentList.get(position);

		// date.setText(objAppointmentSummaryEntity.eventDate);
		date.setText(MgtDateFormat
				.dateFormatMMDDYYYYTIMECalendar(objAppointmentSummaryEntity.eventDate));
		notes.setText(objAppointmentSummaryEntity.description);
		if (objAppointmentSummaryEntity.recurring.equals("0"))
			recurring.setText("none");
		else if (objAppointmentSummaryEntity.recurring.equals("1"))
			recurring.setText("weekly");
		else if (objAppointmentSummaryEntity.recurring.equals("2"))
			recurring.setText("Biweekly");
		else if (objAppointmentSummaryEntity.recurring.equals("3"))
			recurring.setText("monthly");

		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int position = (Integer) v.getTag();
					objAppointmentSummaryEntity = new AppointmentSummaryEntity();
					objAppointmentSummaryEntity = appointmentList.get(position); // getting
																					// object
																					// from
																					// list
																					// by
																					// position
					new EditAppointment((CalendarEvents) context)
							.createEditAppointmentLayout(objAppointmentSummaryEntity);
				} catch (Exception e) {
					System.out.println("<<<Exception>>>" + e.toString()
							+ "<<<>>>" + e.getMessage());
				}
				// Intent intent=new Intent(context,EditAppointment.class);
				// intent.putExtra("AppointmentSummaryEntity",
				// objAppointmentSummaryEntity);
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// context.startActivity(intent);
				// CalendarEvents obj=new CalendarEvents();
				// obj.createEditLayouts("EditAppointmentSummary");
			}
		});
		return convertView;
	}

}
