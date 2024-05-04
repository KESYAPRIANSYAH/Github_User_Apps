package com.dicoding.submissongithub.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissongithub.ui.adapter.FollowAdapter
import com.dicoding.submissongithub.databinding.FragmentFollowingBinding
import com.dicoding.submissongithub.model.DetailUserModel
import com.dicoding.submissongithub.data.remote.response.FollowResponse
import com.dicoding.submissongithub.model.ViewModelFactory
import com.dicoding.submissongithub.ui.detailUser.DetailUser


class FollowingFragment : Fragment() {
    private var position: Int = 1

    private var username: String? = null

    private var _binding: FragmentFollowingBinding? = null
    private val adapter by lazy {
        FollowAdapter()
    }
    private val viewModel by activityViewModels<DetailUserModel> {

        ViewModelFactory.getInstance(requireActivity())

    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIsLoading.observe(viewLifecycleOwner, this::showLoading)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(EXTRA_USERNAME).toString()
        }
        if (position == 1) {

            viewModel.setFollowing(username.toString())
            viewModel.getFollowing.observe(viewLifecycleOwner) { following ->
                adapter.setUserList(following)
            }
        } else {
            viewModel.setFollower(username.toString())
            viewModel.getFollowers.observe(viewLifecycleOwner) { followers ->
                adapter.setUserList(followers)

            }
        }


        binding.recycleclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleclerview.adapter = adapter
        adapter.setOnItemClickCallback { users -> selectedUser(users) }
    }

    private fun selectedUser(user: FollowResponse) {
        Toast.makeText(requireContext(), "You choose ${user.login}", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), DetailUser::class.java)
        intent.putExtra(DetailUser.EXTRA_USERNAME, user.login)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val EXTRA_USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarr.visibility = View.VISIBLE
        } else {
            binding.progressBarr.visibility = View.GONE
        }
    }

}