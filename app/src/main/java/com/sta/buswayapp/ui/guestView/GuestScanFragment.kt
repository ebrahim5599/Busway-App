package com.sta.buswayapp.ui.guestView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sta.buswayapp.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.opengl.Visibility
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuestScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuestScanFragment : Fragment() {
    private lateinit var resultTextView: TextView
    private lateinit var downloadQualityDocs: CardView

    private val scanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED") {
                val barcodeData = intent.getStringExtra("data")
                resultTextView.text = "$barcodeData"
                Toast.makeText(requireContext(), "Scanned: $barcodeData", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_scan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultTextView = view.findViewById(R.id.qrData)
        downloadQualityDocs = view.findViewById(R.id.downloadQualityDocs)
        downloadQualityDocs.visibility = View.GONE
        downloadQualityDocs.setOnClickListener {
            Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED")
        requireContext().registerReceiver(scanReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(scanReceiver)
    }

}