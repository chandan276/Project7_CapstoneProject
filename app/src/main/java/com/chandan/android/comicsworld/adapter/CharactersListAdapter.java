package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.characters.CharactersData;
import com.chandan.android.comicsworld.utilities.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class CharactersListAdapter extends RecyclerView.Adapter<CharactersListAdapter.CharacterListHolder> {

    final private CharacterContentClickListener mOnClickListener;

    private List<CharactersData> charactersDataList = new ArrayList<>();

    public interface CharacterContentClickListener {
        void onCharacterContentClick(int clickedItemIndex);
    }

    public CharactersListAdapter(CharacterContentClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void updateCharactersData(List<CharactersData> charatersData) {
        this.charactersDataList = charatersData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.characters_list_data;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new CharacterListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListHolder characterListHolder, int i) {
        Context context = characterListHolder.characterImageView.getContext();
        CharactersData charactersData = charactersDataList.get(i);

        characterListHolder.characterNameTextView.setText(charactersData.getCharacterName());

        ImageUtils.displayImageFromUrlWithPlaceHolder(context, charactersData.getCharacterImage(),
                characterListHolder.characterImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);
    }

    @Override
    public int getItemCount() {
        return charactersDataList.size();
    }

    class CharacterListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView charcterCardView;
        ImageView characterImageView;
        TextView characterNameTextView;

        CharacterListHolder(View itemView) {
            super(itemView);

            charcterCardView = (CardView) itemView.findViewById(R.id.character_card_view);
            characterImageView = (ImageView) itemView.findViewById(R.id.character_imageView);
            characterNameTextView = (TextView) itemView.findViewById(R.id.character_name);

            charcterCardView.setOnClickListener(this);
        }

        void bind(int listIndex) {

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onCharacterContentClick(clickedPosition);
        }
    }
}
