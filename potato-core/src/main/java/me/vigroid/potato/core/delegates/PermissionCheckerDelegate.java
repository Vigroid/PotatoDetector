package me.vigroid.potato.core.delegates;

import android.Manifest;

import me.vigroid.potato.core.ui.RequestCode;
import me.vigroid.potato.core.ui.scanner.ScannerDelegate;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by vigroid on 10/12/17.
 * Add a permission check layer
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    //qr scan uses camera permissions
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseDelegate delegate) {
        delegate.getSupportDelegate().startForResult(new ScannerDelegate(), RequestCode.SCAN);
    }

    public void startScanWithCheck(BaseDelegate delegate) {
        PermissionCheckerDelegatePermissionsDispatcher.startScanWithPermissionCheck(this, delegate);
    }
}
