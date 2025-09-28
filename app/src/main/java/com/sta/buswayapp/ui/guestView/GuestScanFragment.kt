package com.sta.buswayapp.ui.guestView

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.sta.buswayapp.R

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

        if (arguments != null) {
            val pageTitle = requireArguments().getString("title")
            val textView: TextView = view.findViewById(R.id.guestPageTitle)
            textView.text = pageTitle
        }

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