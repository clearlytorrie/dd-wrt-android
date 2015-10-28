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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rm3l.ddwrt.R;
import org.rm3l.ddwrt.resources.conn.Router;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RouterDuplicateDialogFragment extends RouterUpdateDialogFragment {

    @Override
    protected CharSequence getDialogMessage() {
        return getString(R.string.router_add_msg);
    }

    @Override
    protected CharSequence getPositiveButtonMsg() {
        return getString(R.string.add_router);
    }

    @Override
    protected void onPositiveButtonActionSuccess(@NotNull RouterMgmtDialogListener mListener, int position, boolean error) {
        mListener.onRouterAdd(this, error);
        if (!error) {
            Crouton.makeText(getActivity(), "Item copied as new", Style.CONFIRM).show();
        }
    }

    @Override
    protected boolean isUpdate() {
        return false;
    }

    @Nullable
    @Override
    protected Router onPositiveButtonClickHandler(@NotNull Router router) {
        return this.dao.insertRouter(router);
    }
}
