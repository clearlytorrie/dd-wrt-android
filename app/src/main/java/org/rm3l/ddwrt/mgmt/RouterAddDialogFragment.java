/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Armel S.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.rm3l.ddwrt.mgmt;

import android.app.AlertDialog;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rm3l.ddwrt.R;
import org.rm3l.ddwrt.resources.conn.Router;
import org.rm3l.ddwrt.utils.Utils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class RouterAddDialogFragment extends AbstractRouterMgmtDialogFragment {

    @Override
    protected CharSequence getDialogMessage() {
        return getString(R.string.router_add_msg);
    }

    @Nullable
    @Override
    protected CharSequence getDialogTitle() {
        return null;
    }

    @Override
    protected CharSequence getPositiveButtonMsg() {
        return getString(R.string.add_router);
    }

    @Override
    protected void onPositiveButtonActionSuccess(@NotNull RouterMgmtDialogListener mListener, int position, boolean error) {
        mListener.onRouterAdd(this, error);
        if (!error) {
            Crouton.makeText(getActivity(), "Item added", Style.CONFIRM).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point

        @NotNull final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {

            //Fill the router IP Address with the current gateway address, if any
            try {
                @NotNull final WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager != null) {
                    final DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                    if (dhcpInfo != null) {
                        ((EditText) d.findViewById(R.id.router_add_ip)).setText(Utils.intToIp(dhcpInfo.gateway));
                    }
                }
            } catch (@NotNull final Exception e) {
                e.printStackTrace();
                //No worries
            }
        }
    }

    @Nullable
    @Override
    protected Router onPositiveButtonClickHandler(@NotNull Router router) {
        return this.dao.insertRouter(router);
    }

}
