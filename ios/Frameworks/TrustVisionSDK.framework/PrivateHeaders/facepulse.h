#ifndef LIVENESS_TIMESERIES_H
#define LIVENESS_TIMESERIES_H

#include <deque>
#include <cstring>
#include <sstream>

class FacePulse
{
public:
	std::stringstream msg;
	int* actions;
	int max_steps;
	std::deque<float> l_angle_l_eye;
	std::deque<float> r_angle_l_eye;
	std::deque<float> l_angle_r_eye;
	std::deque<float> r_angle_r_eye;
	std::deque<float> l_angle_mouth;
	std::deque<float> r_angle_mouth;
	std::deque<float> y_angle_head;
	std::deque<float> z_angle_head;
	std::deque<float> l_dist_nose;
	std::deque<float> r_dist_nose;
	std::deque<float> dist_eyes;
	std::deque<float> length_nose;
	std::deque<float> smiling_prob;
	std::deque<float> l_eye_close_prob;
    std::deque<float> r_eye_close_prob;
	
	float l_var_l_eye;
	float r_var_l_eye;
	float l_var_r_eye;
	float r_var_r_eye;
	float l_var_mouth;
	float r_var_mouth;
public:
	FacePulse();
	FacePulse(int maxStep);
	int detect(int action);
	int add_landmarks(float x[], float y[], int n);
	float is_neutral();
	float is_left_eye_blink();
	float is_right_eye_blink();
	float is_turn_left();
	float is_turn_right();
	float is_face_up();
	float is_mouth_moving();
	float is_smiling();
	float is_eyes_closing();
	int* getSteps();
	~FacePulse();
};
#endif

