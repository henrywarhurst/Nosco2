//
//  custom_hmm.cpp
//  hmm_test_cpp
//
//  Created by Henry Warhurst on 16/09/2015.
//  Copyright (c) 2015 Henry Warhurst. All rights reserved.
//

#include "custom_hmm.h"
#include "CvHMM.h"
#include <opencv2/imgproc/imgproc.hpp>
#include <iostream>
#include <algorithm>

int find_mode(const std::vector<int> &vec);

custom_hmm::custom_hmm(int num_subjects)
:num_subjects_(num_subjects)
{
    // Fill out A, B, and pi, the transition matrix, the emission matrix,
    // and the initial matrix, respectively.
    double TRANSdata[num_subjects*num_subjects];
    double EMISdata[num_subjects*num_subjects];
    double INITdata[num_subjects];
    for (int i=0; i<num_subjects; ++i) {
        INITdata[i] = 1.0/(double)(num_subjects);
        for (int j=0; j<num_subjects; ++j) {
            int idx = i*num_subjects + j;
            if (i==j) {
                TRANSdata[idx] = 0.9;
                EMISdata[idx]  = 0.6;
            } else {
                TRANSdata[idx] = 0.1/(double)(num_subjects-1);
                EMISdata[idx]  = 0.4/(double)(num_subjects-1);
            }
        }
    }
    transition_mat_     = cv::Mat(num_subjects,num_subjects,CV_64F,TRANSdata).clone();
    emission_mat_       = cv::Mat(num_subjects,num_subjects,CV_64F,EMISdata).clone();
    initial_mat_        = cv::Mat(1,num_subjects,CV_64F,INITdata).clone();
}

int custom_hmm::get_likely_subject(int seq[], int seq_len)
{
    // Convert the state vector to mat
    cv::Mat seq_mat = cv::Mat(1, seq_len, CV_32S, seq);
    CvHMM hmm;
    //hmm.printModel(transition_mat_, emission_mat_, initial_mat_);
    cv::Mat expected_states;
    hmm.viterbi(seq_mat, transition_mat_, emission_mat_, initial_mat_, expected_states);
//    for (int i=0;i<expected_states.cols;i++) {
//        std::cout << expected_states.at<int>(0,i);
//    }
//    std::cout << std::endl;
    
    // Now find the mode (i.e. most common subject) of the smoothed signal
    std::vector<int> expected_states_vec;
    expected_states_vec.assign((int*) expected_states.datastart, (int*) expected_states.dataend);
    std::sort(expected_states_vec.begin(), expected_states_vec.end());
    int mode = find_mode(expected_states_vec);
//    std::cout << mode << std::endl;
    return mode;
}

// Finds mode of a sorted vector
int find_mode(const std::vector<int> &vec)
{
    int mode = vec[0];
    int max_streak = 1;
    int cur_streak = max_streak;
    int prev_val = vec[0];
    for (int i=1; i<vec.size(); ++i) {
        // Have we changed values or hit the end of the vec?
        if (vec[i] != prev_val) {
            if (cur_streak > max_streak) {
                max_streak = cur_streak;
                mode = prev_val;
            }
            cur_streak = 0;
        } else if (i == vec.size() - 1) {
            cur_streak++;
            if (cur_streak > max_streak) {
                mode = vec[i];
            }
        } else {
            cur_streak++;
        }
        prev_val = vec[i];
    }
    return mode;
}
