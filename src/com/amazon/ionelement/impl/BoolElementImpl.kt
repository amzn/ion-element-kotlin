/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.amazon.ionelement.impl

import com.amazon.ion.IonWriter
import com.amazon.ionelement.api.BoolElement
import com.amazon.ionelement.api.ElementType
import com.amazon.ionelement.api.MetaContainer
import com.amazon.ionelement.api.emptyMetaContainer

internal class BoolElementImpl(
    override val booleanValue: Boolean,
    override val annotations: List<String>,
    override val metas: MetaContainer
): AnyElementBase(), BoolElement {
    override val type: ElementType get() = ElementType.BOOL

    override fun copy(annotations: List<String>, metas: MetaContainer): BoolElement =
        BoolElementImpl(booleanValue, annotations, metas)

    override fun writeContentTo(writer: IonWriter) = writer.writeBool(booleanValue)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoolElementImpl

        if (booleanValue != other.booleanValue) return false
        if (annotations != other.annotations) return false
        // Note: metas intentionally omitted!

        return true
    }

    override fun hashCode(): Int {
        var result = booleanValue.hashCode()
        result = 31 * result + annotations.hashCode()
        // Note: metas intentionally omitted!
        return result
    }

}