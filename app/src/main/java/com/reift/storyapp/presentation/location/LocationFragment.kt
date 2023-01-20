package com.reift.storyapp.presentation.location

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.location.Location
import org.koin.android.viewmodel.ext.android.viewModel


class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    private lateinit var mMap: GoogleMap

    private val boundsBuilder = LatLngBounds.Builder()

    private val viewModel: LocationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)

        setUpMapCallAsync()
        observeLocation()

        return binding.root
    }

    private fun observeLocation() {
        viewModel.getPhotoLocation().observe(viewLifecycleOwner){
            addMarkerPoint(it)
        }
    }

    private fun addMarkerPoint(resource: Resource<List<Location>>) {
        when(resource){
            is Resource.Success -> {
                resource.data?.forEach { loc ->
                    val latLng = LatLng(loc.lat, loc.long)
                    mMap.addMarker(MarkerOptions().position(latLng).title(loc.creator))
                    boundsBuilder.include(latLng)
                }

                val bounds: LatLngBounds = boundsBuilder.build()
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        200
                    )
                )
            }
            else -> {}
        }
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