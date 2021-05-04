package com.muroplankton.testapp.receiptsaver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muroplankton.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.ReceiptViewHolder> {

    private Context context;
    private List<ReceiptData> receipts = new ArrayList<>();

    public ReceiptsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiptViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        ReceiptData receiptData = receipts.get(position);
        Glide.with(context).load(Uri.parse(receiptData.getReceiptUri())).thumbnail(0.1f).into(holder.receiptImageView);
        holder.receiptNameView.setText(receiptData.getReceiptName());
        holder.receiptDateView.setText(receiptData.getReceiptDate());
        holder.receiptID = receiptData.getReceiptID();
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public void setReceipts(List<ReceiptData> receipts) {
        this.receipts = receipts;
    }

    public void clearReceipts() {
        receipts.clear();
    }

    public class ReceiptViewHolder extends RecyclerView.ViewHolder {
        public ImageView receiptImageView;
        public TextView receiptNameView, receiptDateView;
        public String receiptID;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Intent intent = new Intent(ctx, ReceiptActivity.class);
                intent.putExtra("receipt_id", receiptID);
                ctx.startActivity(intent);
            }
        };

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(listener);
            receiptImageView = itemView.findViewById(R.id.receipt_list_item_image);
            receiptNameView = itemView.findViewById(R.id.receipt_list_item_name);
            receiptDateView = itemView.findViewById(R.id.receipt_list_item_date);
        }
    }
}
