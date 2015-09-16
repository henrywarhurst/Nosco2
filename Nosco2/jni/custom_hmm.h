//
//  custom_hmm.h
//  hmm_test_cpp
//
//  Created by Henry Warhurst on 16/09/2015.
//  Copyright (c) 2015 Henry Warhurst. All rights reserved.
//

#ifndef __hmm_test_cpp__custom_hmm__
#define __hmm_test_cpp__custom_hmm__

#include <stdio.h>
#include <opencv2/core/core.hpp>

#ifdef __cplusplus
extern "C" {
#endif

class custom_hmm
{
public:
    custom_hmm(int num_subjects);
    int get_likely_subject(int seq[], int seq_len);
    
private:
    // HMM parameters
    cv::Mat transition_mat_;
    cv::Mat emission_mat_;
    cv::Mat initial_mat_;
    // Number of subjects
    int num_subjects_;
};
    
#ifdef __cplusplus
}
#endif

#endif /* defined(__hmm_test_cpp__custom_hmm__) */
