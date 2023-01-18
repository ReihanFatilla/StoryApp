package com.reift.storyapp.presentation.location

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.reift.storyapp.R
import com.reift.storyapp.databinding.FragmentLocationBinding
import com.reift.storyapp.presentation.MainActivity


class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    private lateinit var mMap: GoogleMap

    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)

        setUpMapCallAsync()


        return binding.root
    }

    private fun setUpMapCallAsync() {


        val onMapReady = OnMapReadyCallback { googleMap ->
            mMap = googleMap

            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.uiSettings.isIndoorLevelPickerEnabled = true
            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true

            setMapStyle()
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.fragment_map) as SupportMapFragment

        mapFragment.getMapAsync(onMapReady)
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.night_map_style))
            if (!success) {
            }
        } catch (exception: Resources.NotFoundException) {
        }
    }
}