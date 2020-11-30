package com.choicely.myapplication.receiptsaver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ReceiptViewHolder> {
    private static final String TAG = "ReceiptListAdapter";
    private static final String INTENT_RECEIPT_ID = "intent_receipt_id";
    private Context context;
    private List<ReceiptData> receiptList = new ArrayList<>();

    public ReceiptListAdapter(Context ctx) {
        context = ctx;
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiptViewHolder(LayoutInflater.from(context).inflate(R.layout.single_receipt_list_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        ReceiptData receipt = receiptList.get(position);

        holder.id = receipt.getId();
        holder.titleText.setText(receipt.getTitle());
        holder.dateText.setText(receipt.getDate());

        File receiptImage = new File(receipt.getImagePath());

        if (receiptImage.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(receiptImage.getAbsolutePath());
            holder.receiptImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public void addReceipt(ReceiptData newReceipt) {
        receiptList.add(newReceipt);
        Log.d(TAG, "receipt added with id: " + newReceipt.getId());
    }

    public void clearReceipts() {
        receiptList.clear();
    }

    public class ReceiptViewHolder extends RecyclerView.ViewHolder {
        public long id;
        public TextView titleText, dateText;
        public ImageView receiptImage;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(onClickListener);
            titleText = itemView.findViewById(R.id.single_receipt_list_card_title);
            dateText = itemView.findViewById(R.id.single_receipt_list_card_date);
            receiptImage = itemView.findViewById(R.id.single_receipt_list_card_image);
        }

        private View.OnClickListener onClickListener = v -> {
            Context ctx = v.getContext();
            Intent intent = new Intent(ctx, SingleReceiptActivity.class);
            intent.putExtra(INTENT_RECEIPT_ID, id);
            ctx.startActivity(intent);
        };
    }
}
