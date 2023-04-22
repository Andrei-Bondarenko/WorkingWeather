package com.example.utils.extensions

import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import com.example.workingweather.R

private const val PREVIOUS_FRAGMENT_TAG_ARG = "PREVIOUS_FRAGMENT_TAG_ARG"

fun Fragment.replace(fragment: Fragment, id:Int) {
    val fragmentManager = requireActivity().supportFragmentManager
    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.addToBackStack(null)
        .replace(id, fragment)
        .commit()
}

fun Fragment.popScreen() {
    requireActivity().hideKeyboard()

    val fragmentManager = parentFragment?.childFragmentManager ?: childFragmentManager
    if (fragmentManager.backStackEntryCount < 2) {
        requireActivity().popFeature()
    } else {
        whenStateAtLeast(Lifecycle.State.STARTED) { fragmentManager.popBackStack() }
    }
}

fun FragmentActivity.popFeature() {
    if (supportFragmentManager.backStackEntryCount < 2) {
        finish()
    } else {
        whenStateAtLeast(Lifecycle.State.STARTED) { supportFragmentManager.popBackStack() }
    }
}

private fun Fragment.getPreviousTag(): String? = arguments?.getString(PREVIOUS_FRAGMENT_TAG_ARG)


fun Fragment.getCurrentScreen(): Fragment? =
    childFragmentManager.findFragmentById(R.id.fragmentContainer)

fun Fragment.replaceScreen(
    fragment: Fragment,
    popCurrent: Boolean = false,
    addToBackStack: Boolean = true,
    requestCode: Int? = null,
    tag: String = fragment::class.java.name,
    fragmentManager: FragmentManager = parentFragment?.childFragmentManager ?: childFragmentManager
) = whenStateAtLeast(Lifecycle.State.STARTED) {
    requireActivity().hideKeyboard()
    fragmentManager.commit {
        if (popCurrent) {
            getCurrentScreen()
                ?.let { it.getPreviousTag() ?: it::class.java.name }
                ?.let { fragment.appendArgs(PREVIOUS_FRAGMENT_TAG_ARG to it) }
        }
        replace(R.id.fragmentContainer, fragment, tag)
        if (addToBackStack) addToBackStack(tag)
        if (requestCode != null) fragment.setTargetFragment(this@replaceScreen, requestCode)
    }
}