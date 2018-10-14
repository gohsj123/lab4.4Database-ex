package my.edu.tarc.demoroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private final List<User> mUserList;
    private LayoutInflater layoutInflater;

    public UserListAdapter(Context context, List<User> mUserList) {
        this.mUserList = mUserList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.user_item, parent, false);

        return new UserViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        String phone = user.getPhone();
        holder.textViewPhone.setText(phone);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public final TextView textViewPhone;
        final UserListAdapter mUserListAdapter;

        public UserViewHolder(View itemView, UserListAdapter adapter) {
            super(itemView);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            this.mUserListAdapter = adapter;
        }
    }
}
