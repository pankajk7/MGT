package com.mygrouptracker.mygrouptracker.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mygrouptracker.mygrouptracker.activity.ExpenseTracker;
import com.mygrouptracker.mygrouptracker.entity.AllEvents;
import com.mygrouptracker.mygrouptracker.entity.AppointmentSummaryEntity;
import com.mygrouptracker.mygrouptracker.entity.CompTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.ExpenseTrackerEntity;
import com.mygrouptracker.mygrouptracker.entity.HolidayEntity;
import com.mygrouptracker.mygrouptracker.entity.KellyDayEntity;
import com.mygrouptracker.mygrouptracker.entity.Links;
import com.mygrouptracker.mygrouptracker.entity.LoginUser;
import com.mygrouptracker.mygrouptracker.entity.MemberGroupType;
import com.mygrouptracker.mygrouptracker.entity.MessageEntity;
import com.mygrouptracker.mygrouptracker.entity.OverTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.PaydayEntity;
import com.mygrouptracker.mygrouptracker.entity.SharingNetwork;
import com.mygrouptracker.mygrouptracker.entity.Shift;
import com.mygrouptracker.mygrouptracker.entity.ShiftRegister;
import com.mygrouptracker.mygrouptracker.entity.ShiftType;
import com.mygrouptracker.mygrouptracker.entity.SickTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.SingleNameListEntity;
import com.mygrouptracker.mygrouptracker.entity.SocialMedia;
import com.mygrouptracker.mygrouptracker.entity.States;
import com.mygrouptracker.mygrouptracker.entity.Station;
import com.mygrouptracker.mygrouptracker.entity.TradeTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.UserType;
import com.mygrouptracker.mygrouptracker.entity.VacationTimeEntity;
import com.mygrouptracker.mygrouptracker.entity.WorkersCompEntity;
import com.mygrouptracker.mygrouptracker.utility.Banners;

public class DataParser {
	Context context;

	public String getStringFromInputStream(InputStream in) {

		if (in == null) {
			;
			return null;
		}

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[1024];
		try {
			for (int i; (i = in.read(b)) != -1;) {
				out.append(new String(b, 0, i));
			}

			String responseString = out.toString();
			if (responseString != null) {
				;
				return out.toString();
			}

		} catch (IOException e) {
			System.out.println(">>> Exception >>> " + e + " >>> Message >>> "
					+ e.getMessage());
		}
		return null;
	}

	public LoginUser parseLoginDetails(InputStream jsonResponseInputStream) {
		return parseLoginDetails(getStringFromInputStream(jsonResponseInputStream));
	}

	public LoginUser parseLoginDetails(String jsonResponseString) {
		LoginUser loginUserObj;
		try {
			if (jsonResponseString != null) {

				loginUserObj = new LoginUser();

				// JSONArray jsonArray = new JSONArray(jsonResponseString);
				JSONObject jsonObject = new JSONObject(jsonResponseString);

				try {
					loginUserObj.userRefNo = jsonObject.getString("UserRefNo");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.userLoginId = jsonObject
							.getString("UserLoginId");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.firstName = jsonObject.getString("FirstName");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.lastName = jsonObject.getString("LastName");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.sessionToken = jsonObject
							.getString("SessionToken");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.badge = jsonObject.getString("Badge");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.memberShiftId = jsonObject
							.getString("MemberShiftId");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.memberTypeId = jsonObject
							.getString("MemberTypeId");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.groupMemberTypeId = jsonObject
							.getString("GroupMemberTypeId");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.type = jsonObject.getString("Type");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}
				try {
					loginUserObj.status = jsonObject.getBoolean("Status");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
				}

				return loginUserObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public ArrayList<AppointmentSummaryEntity> parseAppointmentSummary(
			String jsonResponseString) {
		ArrayList<AppointmentSummaryEntity> appointmentArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);
				appointmentArrayList = new ArrayList<AppointmentSummaryEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					AppointmentSummaryEntity appointmentSummaryEntity = new AppointmentSummaryEntity();

					try {
						appointmentSummaryEntity.id = jsonObject
								.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.entryDate = jsonObject
								.getString("EntryDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.name = jsonObject
								.getString("Name");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.eventDate = jsonObject
								.getString("EventDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.recurring = jsonObject
								.getString("Recurring");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.userId = jsonObject
								.getString("UserId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						appointmentSummaryEntity.description = jsonObject
								.getString("Description");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					appointmentArrayList.add(appointmentSummaryEntity);
				}// End of for
				return appointmentArrayList;
			}// End of if
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<AppointmentSummaryEntity> parseAppointmentSummary(
			InputStream jsonResponseInputStream) {
		return parseAppointmentSummary(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<PaydayEntity> parsePayday(String jsonResponseString) {
		ArrayList<PaydayEntity> paydayArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);
				paydayArrayList = new ArrayList<PaydayEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					PaydayEntity paydayEntity = new PaydayEntity();

					try {
						paydayEntity.Id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						paydayEntity.GroupId = jsonObject.getString("GroupId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						paydayEntity.PayDayName = jsonObject
								.getString("PayDayName");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						paydayEntity.PayDate = jsonObject.getString("PayDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						paydayEntity.StartDate = jsonObject
								.getString("StartDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						paydayEntity.Frequency = jsonObject
								.getString("Frequency");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					paydayArrayList.add(paydayEntity);
				}// End of for
				return paydayArrayList;
			}// End of if
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<PaydayEntity> parsePayday(
			InputStream jsonResponseInputStream) {
		return parsePayday(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<HolidayEntity> parseHoloday(String jsonResponseString) {
		ArrayList<HolidayEntity> holidayArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);
				holidayArrayList = new ArrayList<HolidayEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					HolidayEntity holidayEntity = new HolidayEntity();

					try {
						holidayEntity.Id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						holidayEntity.GroupId = jsonObject.getString("GroupId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						holidayEntity.HolidayType = jsonObject
								.getString("HolidayType");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						holidayEntity.StartDate = jsonObject
								.getString("StartDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						holidayEntity.Frequency = jsonObject
								.getString("Frequency");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					holidayArrayList.add(holidayEntity);
				}// End of for
				return holidayArrayList;
			}// End of if
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<HolidayEntity> parseHoliday(
			InputStream jsonResponseInputStream) {
		return parseHoloday(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<CompTimeEntity> parseCompTime(String jsonResponseString) {
		ArrayList<CompTimeEntity> compTimeArrayList;
		try {
			if (jsonResponseString != null) {
				JSONArray jsonArray = new JSONArray(jsonResponseString);
				compTimeArrayList = new ArrayList<CompTimeEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					CompTimeEntity compTimeEntity = new CompTimeEntity();

					try {
						compTimeEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						compTimeEntity.eventDate = jsonObject
								.getString("EventDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						compTimeEntity.eventType = jsonObject
								.getString("EventType");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						compTimeEntity.hours = jsonObject.getString("Hour");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					// try {
					// compTimeEntity.desc = jsonObject.getString("Note");
					// } catch (Exception exception) {
					// System.out.println(">>> Exception >>> " + exception
					// + " >>> Message >>> " + exception.getMessage());
					// }
					try {
						compTimeEntity.desc = jsonObject
								.getString("Description");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						compTimeEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						compTimeEntity.userId = jsonObject.getString("UserId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					compTimeArrayList.add(compTimeEntity);
				}// End of for
				return compTimeArrayList;
			}// End of if
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<CompTimeEntity> parseCompTime(
			InputStream jsonResponseInputStream) {
		return parseCompTime(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<KellyDayEntity> parseKellyDayEntity(
			String jsonResponseString) {
		ArrayList<KellyDayEntity> kellyDayEntityArrayList = null;
		try {

			JSONArray jsonArray = new JSONArray(jsonResponseString);
			if (jsonArray.length() > 0) {
				kellyDayEntityArrayList = new ArrayList<KellyDayEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					KellyDayEntity kellyDayEntity = new KellyDayEntity();

					try {
						kellyDayEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					try {
						kellyDayEntity.date = jsonObject.getString("Date");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						kellyDayEntity.repeat = jsonObject.getString("Repeat");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						kellyDayEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					// try {
					// kellyDayEntity.date = jsonObject.getString("Date");
					// } catch (Exception exception) {
					// System.out.println(">>> Exception >>> " + exception
					// + " >>> Message >>> " + exception.getMessage());
					// }
					try {
						kellyDayEntity.userId = jsonObject.getString("UserId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					kellyDayEntityArrayList.add(kellyDayEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return kellyDayEntityArrayList;
	}

	public ArrayList<KellyDayEntity> parseKellyDay(
			InputStream jsonResponseInputStream) {
		return parseKellyDayEntity(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<OverTimeEntity> parseOverTimeEntity(
			String jsonResponseString) {
		ArrayList<OverTimeEntity> overTimeArrayList = null;
		try {

			JSONArray jsonArray = new JSONArray(jsonResponseString);
			if (jsonArray.length() > 0) {
				overTimeArrayList = new ArrayList<OverTimeEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					OverTimeEntity overTimeEntity = new OverTimeEntity();

					try {
						overTimeEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.date = jsonObject.getString("Date");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.hours = jsonObject.getString("Hour");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.assignment = jsonObject
								.getString("Assignment");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.desc1 = jsonObject
								.getString("Description1");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.desc2 = jsonObject
								.getString("Description2");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.userId = jsonObject.getString("UserId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.entryDate = jsonObject
								.getString("EntryDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.desc1 = jsonObject
								.getString("Description1");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						overTimeEntity.desc2 = jsonObject
								.getString("Description2");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					overTimeArrayList.add(overTimeEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return overTimeArrayList;
	}

	public ArrayList<OverTimeEntity> parseOverTime(
			InputStream jsonResponseInputStream) {
		return parseOverTimeEntity(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<SickTimeEntity> parseSickTimeEntity(
			String jsonResponseString) {
		ArrayList<SickTimeEntity> sickTimeArrayList = null;
		try {

			JSONArray jsonArray = new JSONArray(jsonResponseString);
			if (jsonArray.length() > 0) {
				sickTimeArrayList = new ArrayList<SickTimeEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					SickTimeEntity sickTimeEntity = new SickTimeEntity();

					try {
						sickTimeEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						sickTimeEntity.date = jsonObject.getString("Date");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						sickTimeEntity.hours = jsonObject.getString("Hour");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						sickTimeEntity.notes = jsonObject.getString("Note");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						sickTimeEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					sickTimeArrayList.add(sickTimeEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sickTimeArrayList;
	}

	public ArrayList<SickTimeEntity> parseSickTime(
			InputStream jsonResponseInputStream) {
		return parseSickTimeEntity(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<TradeTimeEntity> parseTradeTimeEntity(
			String jsonResponseString) {
		ArrayList<TradeTimeEntity> tradeTimeEntityArrayList = null;
		try {

			JSONArray jsonArray = new JSONArray(jsonResponseString);
			if (jsonArray.length() > 0) {
				tradeTimeEntityArrayList = new ArrayList<TradeTimeEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					TradeTimeEntity tradeTimeEntity = new TradeTimeEntity();

					try {
						tradeTimeEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.name = jsonObject.getString("Name");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.date = jsonObject.getString("Date");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.hours = jsonObject.getString("Hour");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.note = jsonObject.getString("Note");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.userId = jsonObject.getString("UserId");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.entryDate = jsonObject
								.getString("EntryDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						tradeTimeEntity.isOweMe = jsonObject
								.getString("IsOweMe");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					tradeTimeEntityArrayList.add(tradeTimeEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tradeTimeEntityArrayList;
	}

	public ArrayList<TradeTimeEntity> parseTradeTime(
			InputStream jsonResponseInputStream) {
		return parseTradeTimeEntity(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<VacationTimeEntity> parseVacationTimeEntity(
			String jsonResponseString) {
		ArrayList<VacationTimeEntity> vacationTimeEntityArrayList = null;
		try {

			JSONArray jsonArray = new JSONArray(jsonResponseString);
			if (jsonArray.length() > 0) {
				vacationTimeEntityArrayList = new ArrayList<VacationTimeEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					VacationTimeEntity vacationTimeEntity = new VacationTimeEntity();

					try {
						vacationTimeEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						vacationTimeEntity.startDate = jsonObject
								.getString("Start");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						vacationTimeEntity.endDate = jsonObject
								.getString("End");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						vacationTimeEntity.hour = jsonObject.getString("Hour");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						vacationTimeEntity.note = jsonObject.getString("Note");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						vacationTimeEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						vacationTimeEntity.entryDate = jsonObject
								.getString("EntryDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					vacationTimeEntityArrayList.add(vacationTimeEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vacationTimeEntityArrayList;
	}

	public ArrayList<VacationTimeEntity> parseVacationTime(
			InputStream jsonResponseInputStream) {
		String str = getStringFromInputStream(jsonResponseInputStream);
		if (str == null) {
			;
			return null;
		}

		return parseVacationTimeEntity(str);
	}

	public ArrayList<WorkersCompEntity> parseWorkersCompEntity(
			String jsonResponseString) {

		ArrayList<WorkersCompEntity> workersCompEntityArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);

				workersCompEntityArrayList = new ArrayList<WorkersCompEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					WorkersCompEntity workersCompEntity = new WorkersCompEntity();

					try {
						workersCompEntity.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						workersCompEntity.injuryDate = jsonObject
								.getString("InjuryDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						workersCompEntity.returnDate = jsonObject
								.getString("ReturnDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						workersCompEntity.caseNumber = jsonObject
								.getString("CaseNumber");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						workersCompEntity.notes = jsonObject.getString("Note");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						workersCompEntity.isShare = jsonObject
								.getString("IsShare");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						workersCompEntity.entryDate = jsonObject
								.getString("EntryDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					workersCompEntityArrayList.add(workersCompEntity);
				}// End of for
				return workersCompEntityArrayList;
			}// end of if
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<WorkersCompEntity> parseWorkersComp(
			InputStream jsonResponseInputStream) {
		return parseWorkersCompEntity(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<ExpenseTrackerEntity> parseExpenseTrackerList(
			InputStream jsonResponseInputStream) {
		return parseExpenseTrackerList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<ExpenseTrackerEntity> parseExpenseTrackerList(
			String jsonResponseString) {

		ArrayList<ExpenseTrackerEntity> expenseTrackerArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);

				expenseTrackerArrayList = new ArrayList<ExpenseTrackerEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ExpenseTrackerEntity expenseTrackerObj = new ExpenseTrackerEntity();

					try {
						expenseTrackerObj.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						expenseTrackerObj.eventDate = jsonObject
								.getString("EventDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						expenseTrackerObj.desc1 = jsonObject
								.getString("Description1");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						expenseTrackerObj.desc2 = jsonObject
								.getString("Description2");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						expenseTrackerObj.desc3 = jsonObject
								.getString("Description3");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						expenseTrackerObj.amount = jsonObject
								.getString("Amount");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					expenseTrackerArrayList.add(expenseTrackerObj);
				}// End of for
				return expenseTrackerArrayList;
			}// end of if
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Station> parseStationList(
			InputStream jsonResponseInputStream) {
		return parseStationList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Station> parseStationList(String jsonResponseString) {

		ArrayList<Station> stationArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);

				stationArrayList = new ArrayList<Station>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Station stationObj = new Station();

					try {
						stationObj.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						stationObj.stationName = jsonObject
								.getString("StationName");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						stationObj.latitude = jsonObject.getString("Latitude");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						stationObj.longitude = jsonObject
								.getString("Longitude");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						stationObj.stationAddress = jsonObject
								.getString("StationAddress");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						stationObj.stationPhone = jsonObject
								.getString("StationPhone");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					stationArrayList.add(stationObj);
				}// End of for
				return stationArrayList;
			}// end of if
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<MessageEntity> parseMessagesList(
			InputStream jsonResponseInputStream) {
		return parseMessagesList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<MessageEntity> parseMessagesList(String jsonResponseString) {

		ArrayList<MessageEntity> messagesArrayList;
		try {
			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);

				messagesArrayList = new ArrayList<MessageEntity>();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					MessageEntity messagesObj = new MessageEntity();

					try {
						messagesObj.id = jsonObject.getString("Id");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						messagesObj.date = jsonObject.getString("EventDate");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						messagesObj.title = jsonObject.getString("Title");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}
					try {
						messagesObj.description = jsonObject
								.getString("Description");
					} catch (Exception exception) {
						System.out.println(">>> Exception >>> " + exception
								+ " >>> Message >>> " + exception.getMessage());
					}

					messagesArrayList.add(messagesObj);
				}// End of for
				return messagesArrayList;
			}// end of if
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Links> parseSocialMediaList(
			InputStream jsonResponseInputStream) {
		return parseSocialMediaList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Links> parseSocialMediaList(String jsonResponseString) {
		ArrayList<Links> linksArrayList = new ArrayList<Links>();
		try {
			Links linksObj = new Links();

			// ArrayList<Links> linksArrayListTemp=new ArrayList<Links>();

			if (jsonResponseString != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseString);
				JSONObject jsonObject = new JSONObject();
				jsonObject = jsonArray.getJSONObject(0);

				// SocialMedia socialMediaObj = new SocialMedia();
				// ArrayList<SocialMedia> socialMediaArrayList=new
				// ArrayList<SocialMedia>();

				// try {
				// linksObj.id = jsonObject.getString("Id");
				// } catch (Exception exception) {
				// System.out.println(">>> Exception >>> " + exception
				// + " >>> Message >>> " + exception.getMessage());
				// }
				// try {
				// linksObj.groupId = jsonObject
				// .getString("GroupId");
				// } catch (Exception exception) {
				// System.out.println(">>> Exception >>> " + exception
				// + " >>> Message >>> " + exception.getMessage());
				// }
				String linkname = "";
				String linkAddress = "";
				int max = (jsonObject.length() / 2) - 1;
				for (int i = 1; i <= max; i++) {
					try {

						linkname = jsonObject.getString("SocialLink0" + i);
						linkAddress = jsonObject.getString("Link0" + i
								+ "Address");
						linksArrayList.add(new Links(linkname, linkAddress));
					} catch (Exception er) {
					}
				}

			}// end of if

			// linksArrayListTemp=linksArrayList;
			// return linksArrayListTemp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return linksArrayList;
	}

	public ArrayList<Links> parseBenefitList(String jsonResponseInputStream) {

		ArrayList<Links> link_benifits = new ArrayList<Links>();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();
				jsonObject = jsonArray.getJSONObject(0);

				String linkname = "";
				String linkAddress = "";
				int max = (jsonObject.length() / 2) - 1;
				for (int i = 1; i <= max; i++) {
					try {

						linkname = jsonObject.getString("BenefitLink0" + i);
						linkAddress = jsonObject.getString("Link0" + i
								+ "Address");
						link_benifits.add(new Links(linkname, linkAddress));
					} catch (Exception er) {
					}
				}

			}
		} catch (Exception er) {

		}

		// return
		// parseBenefitList(getStringFromInputStream(jsonResponseInputStream));
		return link_benifits;
	}

	public ArrayList<Links> parseBenefitList(InputStream jsonResponseInputStream) {
		return parseBenefitList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Links> parseResourceList(String jsonResponseInputStream) {

		ArrayList<Links> linkArrayList = new ArrayList<Links>();
		String linkname = "";
		String linkAddress = "";
		try {

			if (jsonResponseInputStream != null) {
				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();
				jsonObject = jsonArray.getJSONObject(0);

				int max = (jsonObject.length() / 2) - 1;
				for (int i = 1; i <= max; i++) {
					try {

						linkname = jsonObject.getString("ResourceLink0" + i);
						linkAddress = jsonObject.getString("Link0" + i
								+ "Address");
						linkArrayList.add(new Links(linkname, linkAddress));

					} catch (Exception er) {

					}
				}// End of for loop

			}
		} catch (Exception e) {
			System.out.println("<<<Exception>>>" + e.toString() + "<<<>>"
					+ e.getMessage());
		}

		// return
		// parseBenefitList(getStringFromInputStream(jsonResponseInputStream));
		return linkArrayList;
	}

	public ArrayList<Links> parseResourceList(
			InputStream jsonResponseInputStream) {
		return parseResourceList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Links> parseMyGroupList(String jsonResponseInputStream) {

		ArrayList<Links> linkArrayList = new ArrayList<Links>();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();
				jsonObject = jsonArray.getJSONObject(0);

				String linkname = "";
				String linkAddress = "";
				// int max=jsonObject.length()-2;
				// for(int i=1;i<=max;i++){
				try {

					linkname = "LocalGroupLink";
					linkAddress = jsonObject.getString("LocalGroupLink");
					linkArrayList.add(new Links(linkname, linkAddress));

					linkname = "StateGroupLink";
					linkAddress = jsonObject.getString("StateGroupLink");
					linkArrayList.add(new Links(linkname, linkAddress));

					linkname = "NationalGroupLink";
					linkAddress = jsonObject.getString("NationalGroupLink");
					linkArrayList.add(new Links(linkname, linkAddress));

					linkname = "InternationalGroupLink";
					linkAddress = jsonObject
							.getString("InternationalGroupLink");
					linkArrayList.add(new Links(linkname, linkAddress));

					linkname = "PacLink";
					linkAddress = jsonObject.getString("PacLink");
					linkArrayList.add(new Links(linkname, linkAddress));

				} catch (Exception er) {
				}
			}

		} catch (Exception er) {
		}

		// return
		// parseBenefitList(getStringFromInputStream(jsonResponseInputStream));
		return linkArrayList;
	}

	public ArrayList<Links> parseMyGroupList(InputStream jsonResponseInputStream) {
		return parseMyGroupList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Links> parseStoreList(String jsonResponseInputStream) {

		ArrayList<Links> link_benifits = new ArrayList<Links>();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				String linkname = "";
				String linkAddress = "";
				// int max=(jsonObject.length()-2)/2;
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						jsonObject = jsonArray.getJSONObject(i);
						linkname = jsonObject.getString("StoreLink");
						linkAddress = jsonObject.getString("StoreLinkAddress");
						link_benifits.add(new Links(linkname, linkAddress));
					} catch (Exception er) {
					}
				}
				return link_benifits;

			}
		} catch (Exception er) {

		}

		// return
		// parseBenefitList(getStringFromInputStream(jsonResponseInputStream));
		return null;
	}

	public ArrayList<Links> parseStoreList(InputStream jsonResponseInputStream) {
		return parseStoreList(getStringFromInputStream(jsonResponseInputStream));
	}

	// public ArrayList<String> parseShiftList(InputStream
	// jsonResponseInputStream)
	// {
	// return
	// parseShiftTypeList(getStringFromInputStream(jsonResponseInputStream));
	// }
	//
	// public ArrayList<String> parseShiftList(String jsonResponseInputStream)
	// {
	// ArrayList<String> link=new ArrayList<String>();
	//
	// try{
	// if (jsonResponseInputStream != null) {
	//
	// JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
	// JSONObject jsonObject = new JSONObject();
	//
	// String linkname="";
	// String linkAddress="";
	// for(int i=0;i<jsonArray.length();i++){
	// try
	// {
	// jsonObject=jsonArray.getJSONObject(i);
	// linkname=jsonObject.getString("ShiftName");
	// linkname=jsonObject.getString("StartDate");
	// linkname=jsonObject.getString("HoursPerDay");
	// for(int j=0;j<6;j++)
	// {
	// linkname=jsonObject.getString("Day0"+i);
	// linkname=jsonObject.getString("Day0"+i+"ColorName");
	// linkname=jsonObject.getString("Day0"+i+"ColorName");
	// }
	// }
	// catch(Exception er){
	//
	// }
	// }//End of outer for
	//
	//
	// }
	// }
	// catch(Exception er)
	// {
	//
	// }
	// return null;
	// }

	public ArrayList<Banners> parseBannersList(
			InputStream jsonResponseInputStream) {
		return parseBannersList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Banners> parseBannersList(String jsonResponseInputStream) {
		ArrayList<Banners> bannersArrayList = new ArrayList<Banners>();
		// Banners objBanners=new Banners();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = new JSONObject();
					jsonObject = jsonArray.getJSONObject(i);
					Banners objBanners = new Banners();
					objBanners.id = jsonObject.getString("Id");
					objBanners.entryDate = jsonObject.getString("EntryDate");
					objBanners.imageLink = jsonObject.getString("ImageLink");
					objBanners.description = jsonObject
							.getString("Description");
					objBanners.link = jsonObject.getString("Link");
					bannersArrayList.add(objBanners);
				}// end of for
				return bannersArrayList;
			}
		} catch (Exception e) {
			System.out.print(">>>DataParser_parseBannersList>>>" + e.toString()
					+ ">>>" + e.getMessage());
		}
		return null;
	}

	public ArrayList<MemberGroupType> parseMemberGroupTypeList(
			InputStream jsonResponseInputStream) {
		return parseMemberGroupTypeList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<MemberGroupType> parseMemberGroupTypeList(
			String jsonResponseInputStream) {
		ArrayList<MemberGroupType> link = new ArrayList<MemberGroupType>();
		MemberGroupType objMemberGroupType = new MemberGroupType();
		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						objMemberGroupType = new MemberGroupType();
						jsonObject = jsonArray.getJSONObject(i);
						objMemberGroupType.groupName = jsonObject
								.getString("GroupName");
						objMemberGroupType.id = jsonObject.getString("Id");
						link.add(objMemberGroupType);
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>" + e.getMessage());
					}
				}
				return link;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}
	
	public ArrayList<UserType> parseUserTypeList(
			InputStream jsonResponseInputStream) {
		return parseUserTypeList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<UserType> parseUserTypeList(
			String jsonResponseInputStream) {
		ArrayList<UserType> link = new ArrayList<UserType>();
		UserType userTypeObj = new UserType();
		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						userTypeObj = new UserType();
						jsonObject = jsonArray.getJSONObject(i);
						userTypeObj.type = jsonObject
								.getString("Type");
						userTypeObj.id = jsonObject.getString("Id");
						link.add(userTypeObj);
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>" + e.getMessage());
					}
				}
				return link;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}
	
	
	public ArrayList<States> parseStatesList(
			InputStream jsonResponseInputStream) {
		return parseStatesList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<States> parseStatesList(
			String jsonResponseInputStream) {
		ArrayList<States> link = new ArrayList<States>();
		States statesObj = new States();
		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						statesObj = new States();
						jsonObject = jsonArray.getJSONObject(i);
						statesObj.id = jsonObject
								.getString("Id");						
						statesObj.stateAbbreviation = jsonObject.getString("StateAbbreviation");
						statesObj.stateName = jsonObject.getString("StateName");
						link.add(statesObj);
					} catch (Exception e) {
						System.out.println(">>>Exception>>>" + e.toString()
								+ ">>>" + e.getMessage());
					}
				}
				return link;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}
	

	public ArrayList<ShiftType> parseShiftTypeList(
			InputStream jsonResponseInputStream) {
		return parseShiftTypeList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<ShiftType> parseShiftTypeList(
			String jsonResponseInputStream) {
		ArrayList<ShiftType> link = new ArrayList<ShiftType>();
		ShiftType objShiftType = new ShiftType();
		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						objShiftType = new ShiftType();
						jsonObject = jsonArray.getJSONObject(i);
						objShiftType.idString = jsonObject.getString("Id");
						objShiftType.memberName = jsonObject
								.getString("MemberName");
						link.add(objShiftType);
					} catch (Exception er) {
					}
				}
				return link;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}

	public ArrayList<Shift> parseShiftList(InputStream jsonResponseInputStream) {
		return parseShiftList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<Shift> parseShiftList(String jsonResponseInputStream) {
		ArrayList<Shift> link = new ArrayList<Shift>();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						jsonObject = jsonArray.getJSONObject(i);

						link.add(new Shift(jsonObject.getString("Id"),
								jsonObject.getString("ShiftName")));

						// link.add(new Shift(jsonObject.getString("Id"),
						// jsonObject.getString("ShiftName"), jsonObject
						// .getString("StartDate"), jsonObject
						// .getString("HoursPerDay"), jsonObject
						// .getString("Day01"), jsonObject
						// .getString("Day01ColorName"),
						// jsonObject.getString("Day01ColorCode"),
						// jsonObject.getString("Day02"), jsonObject
						// .getString("Day02ColorName"),
						// jsonObject.getString("Day02ColorCode"),
						// jsonObject.getString("Day03"), jsonObject
						// .getString("Day03ColorName"),
						// jsonObject.getString("Day03ColorCode"),
						// jsonObject.getString("Day04"), jsonObject
						// .getString("Day04ColorName"),
						// jsonObject.getString("Day04ColorCode"),
						// jsonObject.getString("Day05"), jsonObject
						// .getString("Day05ColorName"),
						// jsonObject.getString("Day05ColorCode"),
						// jsonObject.getString("Day06"), jsonObject
						// .getString("Day06ColorName"),
						// jsonObject.getString("Day06ColorCode")));

					} catch (Exception er) {
						er.printStackTrace();
					}
				}
				return link;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}

	public Shift parseShift(InputStream jsonResponseInputStream) {
		return parseShift(getStringFromInputStream(jsonResponseInputStream));
	}

	public Shift parseShift(String jsonResponseInputStream) {
		Shift shiftObj = new Shift();

		try {
			if (jsonResponseInputStream != null) {
//				jsonResponseInputStream = jsonResponseInputStream.replace("NULL", "0");
//				jsonResponseInputStream = jsonResponseInputStream.replace("null", "0");				

				// JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject(jsonResponseInputStream);

				// for (int i = 0; i < jsonArray.length(); i++) {
				// try{
				// jsonObject = jsonArray.getJSONObject(i);
				// try {
				// shiftObj.shiftName = jsonObject.getString("ShiftName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				try {
					shiftObj.startDate = jsonObject.getString("StartDate");
				} catch (Exception e) {
					System.out.println(">>> Exception >>> " + e
							+ " >>> Message >>> " + e.getMessage());
				}
				try {
					shiftObj.shiftName = jsonObject.getString("ShiftName");
				} catch (Exception e) {
					System.out.println(">>> Exception >>> " + e
							+ " >>> Message >>> " + e.getMessage());
				}

				try {
					shiftObj.day = new ArrayList<String>();
					shiftObj.ColorCode = new ArrayList<String>();
					shiftObj.ColorName = new ArrayList<String>();
					
					for (int i = 1; i <= 18; i++) {
						try {
							if (i < 10) {
								// String dayString = String.valueOf(i);
								String day = "Day0"+ i;
								day = jsonObject.getString(day);
								shiftObj.day.add(day);
								shiftObj.ColorCode.add(jsonObject
										.getString("Day0" + i + "ColorCode"));
								shiftObj.ColorName.add(jsonObject
										.getString("Day0" + i + "ColorName"));
							} else {
								shiftObj.day.add(jsonObject
										.getString("Day" + i));
								shiftObj.ColorCode.add(jsonObject
										.getString("Day" + i + "ColorCode"));
								shiftObj.ColorName.add(jsonObject
										.getString("Day" + i + "ColorName"));
							}
						} catch (Exception er) {
							er.printStackTrace();
						}
					}// end of for loop
				} catch (Exception e) {
					System.out.println(">>> Exception >>> " + e
							+ " >>> Message >>> " + e.getMessage());
				}
				// try{
				// shiftObj.day01ColorName = jsonObject
				// .getString("Day01ColorName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day01ColorCode =
				// jsonObject.getString("Day01ColorCode");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day02 = jsonObject.getString("Day02");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day02ColorName =
				// jsonObject.getString("Day02ColorName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day02ColorCode =
				// jsonObject.getString("Day02ColorCode");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day03 = jsonObject.getString("Day03");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day03ColorName = jsonObject
				// .getString("Day03ColorName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day03ColorCode =
				// jsonObject.getString("Day03ColorCode");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day04 = jsonObject.getString("Day04");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day04ColorName = jsonObject
				// .getString("Day04ColorName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day04ColorCode =
				// jsonObject.getString("Day04ColorCode");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day05 = jsonObject.getString("Day05");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day05ColorName = jsonObject
				// .getString("Day05ColorName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day05ColorCode =
				// jsonObject.getString("Day05ColorCode");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day06 = jsonObject.getString("Day06");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day06ColorName = jsonObject
				// .getString("Day06ColorName");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// try{
				// shiftObj.day06ColorCode =
				// jsonObject.getString("Day06ColorCode");
				// } catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// }catch (Exception e) {
				// System.out.println(">>> Exception >>> " + e
				// + " >>> Message >>> " + e.getMessage());
				// }
				// }//end of for loop
				return shiftObj;
			}// End of if

		} catch (Exception e) {
			System.out.println(">>> Exception >>> " + e + " >>> Message >>> "
					+ e.getMessage());
		}
		return null;
	}

	public ArrayList<SharingNetwork> parseSharingNetworkList(
			InputStream jsonResponseInputStream) {
		return parseSharingNetworkList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<SharingNetwork> parseSharingNetworkList(
			String jsonResponseInputStream) {
		ArrayList<SharingNetwork> link = new ArrayList<SharingNetwork>();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						jsonObject = jsonArray.getJSONObject(i);

						link.add(new SharingNetwork(jsonObject.getString("Id"),
								jsonObject.getString("EventDate"), jsonObject
										.getString("Email")));

					} catch (Exception er) {
					}
				}// End of for
				return link;

			}// End of if
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}

	public ArrayList<ShiftRegister> parseShiftRegisterList(
			InputStream jsonResponseInputStream) {
		return parseShiftRegisterList(getStringFromInputStream(jsonResponseInputStream));
	}

	public ArrayList<ShiftRegister> parseShiftRegisterList(
			String jsonResponseInputStream) {
		ArrayList<ShiftRegister> link = new ArrayList<ShiftRegister>();

		try {
			if (jsonResponseInputStream != null) {

				JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						jsonObject = jsonArray.getJSONObject(i);
						link.add(new ShiftRegister(jsonObject.getString("Id"),
								jsonObject.getString("ShiftName")));

					} catch (Exception e) {
						Log.e(">>>ShiftRegister>>>",
								e.toString() + ">>>" + e.getMessage());
					}
				}
				return link;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
		}
		return null;
	}

	public AllEvents parseAllEvents(InputStream jsonResponseInputStream) {
		return parseAllEvents(getStringFromInputStream(jsonResponseInputStream));
	}

	public AllEvents parseAllEvents(String jsonResponseInputStream) {
		AllEvents objAllEvents = new AllEvents();

		try {
			if (jsonResponseInputStream != null) {

				// JSONArray jsonArray = new JSONArray(jsonResponseInputStream);
				JSONObject jsonObject = new JSONObject(jsonResponseInputStream);
				JSONArray jsonTempArray;
				JSONObject jsonTempObject;
				//
				try {
					// jsonObject=jsonObject.getJSONObject(0);
					String str;
					;
					if (jsonObject.get("Appointments") != null) {
						str = jsonObject.get("Appointments").toString();
						objAllEvents.appointmentArrayList = parseAppointmentSummary(str);
					}

					if (jsonObject.get("CompTimes") != null) {
						str = jsonObject.get("CompTimes").toString();
						objAllEvents.compTimeArrayList = parseCompTime(str);
					}
					// jsonTempArray=jsonObject.getJSONArray("CompTimes");
					// if(jsonTempArray!=null){
					// objAllEvents.compTimeArrayList=parseCompTime(jsonTempArray.toString());
					// }

					if (jsonObject.get("KellyDays") != null) {
						str = jsonObject.get("KellyDays").toString();
						objAllEvents.kellyDayArrayList = parseKellyDayEntity(str);
					}

					// jsonTempArray=jsonObject.getJSONArray("KellyDays");
					// if(jsonTempArray!=null){
					// objAllEvents.kellyDayArrayList=parseKellyDayEntity(jsonTempArray.toString());
					// }

					if (jsonObject.get("OverTimes") != null) {
						str = jsonObject.get("OverTimes").toString();
						objAllEvents.overTimeArrayList = parseOverTimeEntity(str);
					}

					// jsonTempArray=jsonObject.getJSONArray("OverTimes");
					// if(jsonTempArray!=null){
					// objAllEvents.overTimeArrayList=parseOverTimeEntity(jsonTempArray.toString());
					// }

					if (jsonObject.get("SickTimes") != null) {
						str = jsonObject.get("SickTimes").toString();
						objAllEvents.sickTimeArrayList = parseSickTimeEntity(str);
					}

					if (jsonObject.get("TradeTimes") != null) {
						str = jsonObject.get("TradeTimes").toString();
						objAllEvents.tradeTimeArrayList = parseTradeTimeEntity(str);
					}

					// jsonTempArray=jsonObject.getJSONArray("TradeTimes");
					// if(jsonTempArray!=null){
					// objAllEvents.tradeTimeArrayList=parseTradeTimeEntity(jsonTempArray.toString());
					// }
					if (jsonObject.get("Vacationtimes") != null) {
						str = jsonObject.get("Vacationtimes").toString();
						objAllEvents.vacationTimeArrayList = parseVacationTimeEntity(str);
					}

					if (jsonObject.get("WorkersComps") != null) {
						str = jsonObject.get("WorkersComps").toString();
						objAllEvents.workersCompArrayList = parseWorkersCompEntity(str);
					}

					// jsonTempArray=jsonObject.getJSONArray("WorkersComps");
					// if(jsonTempArray!=null){
					// objAllEvents.workersCompArrayList=parseWorkersCompEntity(jsonTempArray.toString());
					// }

				} catch (Exception e) {
					// Log.e(">>>All events Exception>>>",
					// e.toString()+">>>"+e.getMessage());
					System.out.println(">>>Exception>>>" + e.toString()
							+ ">>>Message>>>" + e.getMessage());
				}

				return objAllEvents;

			}
		} catch (Exception e) {
			System.out.println(">>>Exception>>>" + e.toString() + ">>>"
					+ e.getMessage());
			Log.e(">>>Exception>>>", e.toString() + ">>>" + e.getMessage());
		}
		return null;
	}

}
