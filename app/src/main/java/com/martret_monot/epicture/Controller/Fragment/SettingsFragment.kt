package com.martret_monot.epicture.Controller.Fragment

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.martret_monot.epicture.R
import kotlinx.android.synthetic.main.fragment_settings.*
import android.widget.RadioButton
import android.widget.RadioGroup
import com.martret_monot.epicture.Controller.AuthVC
import com.martret_monot.epicture.Controller.HomeVC
import com.martret_monot.epicture.Preference
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    var radioGroupSection: RadioGroup? = null
    var radioGroupSort: RadioGroup? = null
    var saveButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val preference = Preference(this.requireContext())
        val arraySection = hashMapOf<String, Int>("hot" to R.id.hotRb,
            "top" to R.id.topRb,
            "user" to R.id.userRb)

        val arraySort = hashMapOf<String, Int>("viral" to R.id.viralRb,
            "top" to R.id.topSortRb,
            "time" to R.id.timeRb,
            "rising" to R.id.risingRb)

        val section: String = preference.getRequestSection()
        val sort: String = preference.getRequestSort()

        radioGroupSection = view?.findViewById(R.id.sectionRadioButton)
        radioGroupSort = view?.findViewById(R.id.sortRadioButton)
        saveButton = view?.findViewById(R.id.saveButton)

        for ((key, value) in arraySection) {
            if (section == key) {
                radioGroupSection?.check(value)
            }
        }

        for ((key, value) in arraySort) {
            if (sort == key) {
                radioGroupSort?.check(value)
            }
        }

        view.saveButton.setOnClickListener { view ->
            updatePreference()
        }

        view.logoutButton.setOnClickListener { view ->
            logout()
        }
        return view
    }

    private fun updatePreference() {

        val preference = Preference(this.requireContext())
        val arraySectionReverse = hashMapOf<Int, String>(R.id.hotRb to "hot",
            R.id.topRb to "top",
            R.id.userRb to "user")

        val arraySortReverse = hashMapOf<Int, String>(R.id.viralRb to "viral",
            R.id.topSortRb to "top",
            R.id.timeRb to "time",
            R.id.risingRb to "rising")

        val checkSection = radioGroupSection?.checkedRadioButtonId
        val checkSort = radioGroupSort?.checkedRadioButtonId
        val section = arraySectionReverse[checkSection]
        val sort = arraySortReverse[checkSort]
        preference.setRequestSection(section)
        preference.setRequestSort(sort)

        Toast.makeText(requireContext(), "$section, $sort saved", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        val sharedPref = Preference(requireContext().applicationContext)
        sharedPref.clear(requireContext().applicationContext)
    }
}
