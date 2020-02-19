package com.tech.cloudnausor.ohmytennis.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.model.BookArray;
import com.tech.cloudnausor.ohmytennis.model.ClubAvabilityModelData;
import com.tech.cloudnausor.ohmytennis.model.RequestToBook;
import com.tech.cloudnausor.ohmytennis.response.ResevertionTimeResponseData;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubAvaibilityAdapterData extends RecyclerView.Adapter<ClubAvaibilityAdapterData.ClubAvaibilityDataHolder> {
    Context context;
    ArrayList<ClubAvabilityModelData> clubAvabilityModelDataDetails;
    ItemClickListenerSelection mClickListener;
    FragmentActivity fragmentActivity ;
    String enable ="";
    String coach_name= "";
    String place = "";
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_="";
    ApiInterface apiInterface;


    public ClubAvaibilityAdapterData(Context context, ArrayList<ClubAvabilityModelData> clubAvabilityModelDataDetails, FragmentActivity fragmentActivity,String enable,String coach_name,String place ) {
        this.context = context;
        this.clubAvabilityModelDataDetails = clubAvabilityModelDataDetails;
        this. fragmentActivity = fragmentActivity ;
        this.enable = enable;
        this.coach_name= coach_name;
        this.place = place;
        sharedPreferences = context.getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(context);
        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    @NonNull
    @Override
    public ClubAvaibilityDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_layout,parent,false);
        return new ClubAvaibilityDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubAvaibilityDataHolder holder, int position) {
        ClubAvabilityModelData clubAvabilityModelData = clubAvabilityModelDataDetails.get(position);

        holder.Details_Time_start.setText(clubAvabilityModelData.getStartTime());
        holder.Details_Time_end.setText(clubAvabilityModelData.getEndTime());
        holder.Details_max_person.setText(clubAvabilityModelData.getMaxCount());
        holder.Details_price.setText(clubAvabilityModelData.getPrice());
        if(enable.equals("1")){
            holder.Details_Edit.setEnabled(true);
        }else if(enable.equals("0")){
            holder.Details_Edit.setEnabled(false);
        }
        holder.TimeandDate.setText(clubAvabilityModelDataDetails.get(position).getCourse());
        holder.changetimename.setText("Couse");
        holder.avaibility_main_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.alertDialog.dismiss();
            }
        });
        holder.avaibility_main_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(),selectionTimeDetails.get(0).getArraySelectionTime(),Toast.makeText())
//                    selectionTimeDetails.get(0).getArraySelectionTime();
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);

                ArrayList<RequestToBook> requestToBooks = new ArrayList<RequestToBook>();
                requestToBooks.clear();
                requestToBooks.add(new RequestToBook(clubAvabilityModelDataDetails.get(position).getCoachId(), "CoursCollectifClub", formattedDate, clubAvabilityModelDataDetails.get(position).getStartTime()+" "+clubAvabilityModelDataDetails.get(position).getEndTime(), userid_, clubAvabilityModelDataDetails.get(position).getPrice(),clubAvabilityModelDataDetails.get(position).getCourse()));

                BookArray bookArray = new BookArray();
                bookArray.setRequestToBooks(requestToBooks);
                System.out.println("bookarray "+new Gson().toJson(bookArray));
                apiInterface.setreservation(bookArray).enqueue(new Callback<ResevertionTimeResponseData>() {
                    @Override
                    public void onResponse(Call<ResevertionTimeResponseData> call, Response<ResevertionTimeResponseData> response) {
                        if(response.body().getIsSuccess().equals("true")){
                            holder.alertDialog.dismiss();
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResevertionTimeResponseData> call, Throwable t) {

                    }
                });
            }
        });
holder.priceShow.setText(clubAvabilityModelDataDetails.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return clubAvabilityModelDataDetails.size();
    }

    public class ClubAvaibilityDataHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView Details_Time_start,Details_Time_end,Details_max_person,Details_price;
        Button Details_Edit;
        AlertDialog.Builder dialogBuilder;
        LayoutInflater inflater;
        AlertDialog alertDialog;
        View dialogView;
        LinearLayout placelayout,pricelayout;
        String timedatestring = "";
        TextView placeShow,TimeandDate,TypeofService,priceShow,coachname,changetimename;
        Button avaibility_main_ok,avaibility_main_cancel;


        public ClubAvaibilityDataHolder(@NonNull View itemView) {
            super(itemView);

                    Details_Edit = (Button)itemView.findViewById(R.id.details_edit);
                    Details_Time_start = (TextView) itemView.findViewById(R.id.details_start);
                    Details_Time_end = (TextView) itemView.findViewById(R.id.details_end);
                    Details_max_person = (TextView) itemView.findViewById(R.id.details_max_person);
                    Details_price = (TextView) itemView.findViewById(R.id.details_price);

            dialogBuilder = new AlertDialog.Builder(fragmentActivity);
            inflater = LayoutInflater.from(context);
            dialogView = inflater.inflate(R.layout.bookingindiviconfirm, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            placeShow= (TextView)dialogView.findViewById(R.id.placeShow) ;
            TimeandDate= (TextView)dialogView.findViewById(R.id.TimeandDate) ;
            placelayout= (LinearLayout) dialogView.findViewById(R.id.placelayout) ;
            pricelayout= (LinearLayout) dialogView.findViewById(R.id.pricelayout) ;
            TypeofService= (TextView)dialogView.findViewById(R.id.TypeofService) ;
            priceShow= (TextView)dialogView.findViewById(R.id.priceShow) ;
            coachname= (TextView)dialogView.findViewById(R.id.coachname) ;
            changetimename= (TextView)dialogView.findViewById(R.id.changetimename) ;
            avaibility_main_ok = (Button) dialogView.findViewById(R.id.avaibility_main_ok) ;
            avaibility_main_cancel = (Button) dialogView.findViewById(R.id.avaibility_main_cancel) ;
            TypeofService.setText("Cours Collectif Club");
            placeShow.setText(place);
            coachname.setText(coach_name);
//            TimeandDate.setText(clubAvabilityModelDataDetails.get(position).getCourse());
//            changetimename.setText("Couse");
//            avaibility_main_cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alertDialog.dismiss();
//                }
//            });
//            avaibility_main_ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                        Toast.makeText(getApplicationContext(),selectionTimeDetails.get(0).getArraySelectionTime(),Toast.makeText())
////                    selectionTimeDetails.get(0).getArraySelectionTime();
//
//
//                    ArrayList<RequestToBook> requestToBooks = new ArrayList<RequestToBook>();
//                                 requestToBooks.clear();
//                                requestToBooks.add(new RequestToBook(clubAvabilityModelDataDetails.get(position).getCoachId(), "CollectivClub", clubAvabilityModelDataDetails.get(position).getWeekday(), clubAvabilityModelDataDetails.get(position).getStartTime()+" "+clubAvabilityModelDataDetails.get(position).getEndTime(), userid_, clubAvabilityModelDataDetails.get(position).getPrice(),clubAvabilityModelDataDetails.get(position).getCourse()));
//
//                    BookArray bookArray = new BookArray();
//                    bookArray.setRequestToBooks(requestToBooks);
//                    System.out.println("bookarray "+new Gson().toJson(bookArray));
//                    apiInterface.setreservation(bookArray).enqueue(new Callback<ResevertionTimeResponseData>() {
//                        @Override
//                        public void onResponse(Call<ResevertionTimeResponseData> call, Response<ResevertionTimeResponseData> response) {
//                            if(response.body().getIsSuccess().equals("true")){
//
//                                alertDialog.dismiss();
//
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<ResevertionTimeResponseData> call, Throwable t) {
//
//                        }
//                    });
//                }
//            });

               Details_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.show();
                }
               });

//                    Details_Edit.setClickable(false);
//                    itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.itemClickListerner(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListenerSelection itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListenerSelection{
        void itemClickListerner(View view, int position);
    }
}
