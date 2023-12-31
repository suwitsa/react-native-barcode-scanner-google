package com.spyworldxp.barcodescanner;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.spyworldxp.barcodescanner.ui.BarcodeScannerView;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * React Native ViewManager corresponding to BarcodeScannerView
 */

public class BarcodeScannerManager extends SimpleViewManager<BarcodeScannerView> {

    private BarcodeScannerView mBarcodeScannerView;
    public static int RC_HANDLE_CAMERA_PERM = 176; // must be < 256

    @Override
    public String getName() {
        return "RCTBarcodeScannerManager";
    }

    @Override
    protected BarcodeScannerView createViewInstance(ThemedReactContext reactContext) {
        mBarcodeScannerView = new BarcodeScannerView(reactContext);
        return mBarcodeScannerView;
    }

    public BarcodeScannerView getBarcodeScannerView() {
        return mBarcodeScannerView;
    }

    /*
     * -----------------------------------
     * ------------- Props ---------------
     * -----------------------------------
     */

    // Barcode types as a bitmask
    @ReactProp(name = "barcodeTypes", defaultInt = 0)
    public void setBarcodeTypes(BarcodeScannerView view, int barcodeTypes) {
        view.setBarcodeTypes(barcodeTypes);
    }

    // Focus modes
    // Possible values: 0 = continuous focus (if supported), 1 = tap-to-focus (if supported), 2 = fixed focus
    @ReactProp(name = "focusMode", defaultInt = 0)
    public void setFocusMode(BarcodeScannerView view, int focusMode) {
        view.setFocusMode(focusMode);
    }

    @ReactProp(name = "cameraFillMode", defaultInt = 0)
    public void setCameraFillMode(BarcodeScannerView view, int cameraFillMode) {
        view.setCameraFillMode(cameraFillMode);
    }

    // Precision modes
    // Possible values: 0 = more precise, 1 = more responsive
    @ReactProp(name = "precisionMode", defaultInt = 0)
    public void setPrecisionMode(BarcodeScannerView view, int precisionMode) {
        view.setPrecisionMode(precisionMode);
    }

    // Torch modes (for enabling torch mode)
    // Possible values: 0 = off, 1 = on
    @ReactProp(name = "flashMode", defaultInt = 0)
    public void setFlashhMode(BarcodeScannerView view, int flashMode) {
        view.setFlashMode(flashMode);
    }
    
    /**
     * Handle results from requestPermissions.
     * Call this method from MainActivity.java in your React Native app or implement a version of your own that checks for the camera permission.
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            // The permission result doesn't concern this app
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // we have permission, so create the camerasource
            mBarcodeScannerView.init();
            return;
        }

        // Permission was not granted. Show a message
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("No permission")
            .setMessage("No permission.")
            .setPositiveButton("Ok", null)
            .show();
    }
}
