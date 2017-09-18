package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    // Plays pronounciation of Mivok words
    private MediaPlayer mMediaPlayer;
    // Getting Audio manager to get audio stream
    private AudioManager mAudioManager;
    // Setting up audio focus change listener
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                // Release resources
                releaseMediaPlayer();
            } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mMediaPlayer.start();
            }

        }
    };
    // Release the Media player once the audio file is completed
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    // Constructor
    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Storing Words to be displayed on the ListView
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        // Setting Custom WordAdapter
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_family);

        ListView listView = container.findViewById(R.id.list);

        if (listView == null)
            listView = (ListView) inflater.inflate(R.layout.word_list, container, false);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int audioFile = words.get(i).getMivokPronounciation();

                // Release any previous media player
                releaseMediaPlayer();

                // Initializing Audio Manager
                mAudioManager = (AudioManager)
                        getActivity().getSystemService(Context.AUDIO_SERVICE);

                // Setting stream to transient music type
                int requestResult = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // Only If stream is available setup media player and play
                if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Create a new media player
                    mMediaPlayer = MediaPlayer.create(getActivity(), audioFile);

                    // Release Media player on audio completion
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    // Finally start the file
                    mMediaPlayer.start();

                    // Create a new media player
                    mMediaPlayer = MediaPlayer.create(getActivity(), audioFile);

                    mMediaPlayer.start();

                    // Release Media plaer on audio completion
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return listView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Release Audio Stream once playback is either interrupted or stopped
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    /*
     * Stop media player as soon as user leaves this activity
     */
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
