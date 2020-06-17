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

package com.amazon.ionelement.api

import com.amazon.ion.IonReader
import com.amazon.ionelement.impl.IonElementLoaderImpl

/**
 * Provides a several functions for loading Ion data from an [IonReader].
 */
interface IonElementLoader {
    /**
     * Reads a single element from the specified Ion text data.
     *
     * Throws an [IllegalArgumentException] if there are multiple top level elements.
     */
    fun loadSingleElement(ionText: String): AnyElement

    /**
     * Reads the next element from the specified [IonReader].
     *
     * Expects [ionReader] to be positioned *before* the element to be read.
     *
     * If there are additional elements to be read after reading the next element,
     * throws an [IllegalArgumentException].
     */
    fun loadSingleElement(ionReader: IonReader): AnyElement

    /**
     * Reads all elements remaining to be read from the [IonReader].
     *
     * Expects [ionReader] to be positioned *before* the first element to be read.
     *
     * Avoid this function when reading large amounts of Ion because a large amount of memory will be consumed.
     * Instead, prefer [loadCurrentElement].
     */
    fun loadAllElements(ionReader: IonReader): Iterable<AnyElement>

    /**
     * Reads all of the elements in the specified Ion text data.
     *
     * Avoid this function when reading large amounts of Ion because a large amount of memory will be consumed.
     * Instead, prefer [processAll] or [loadCurrentElement].
     */
    fun loadAllElements(ionText: String): Iterable<AnyElement>

    /**
     * Reads the current element from the specified [IonReader].  Does not close the [IonReader].
     *
     * Expects [ionReader] to be positioned *on* the element to be read--does not call [IonReader.next].
     *
     * This method can be utilized to fetch and process the elements one by one and can help avoid high memory
     * consumption when processing large amounts of Ion data.
     */
    fun loadCurrentElement(ionReader: IonReader): AnyElement
}

/**
 * Specifies options for [IonElementLoader].
 *
 * While there is only one property here currently, new properties may be added to this class without breaking
 * source compatibility with prior versions of this library.
 */
data class IonElementLoaderOptions(
    /**
     * Set to true to cause `IonLocation` to be stored in the [IonElement.metas] collection of all elements loaded.
     *
     * This is `false` by default because it has a performance penalty.
     */
    val includeLocationMeta: Boolean = false
)

/** Creates an [IonElementLoader] implementation with the specified [options]. */
fun createIonElementLoader(options: IonElementLoaderOptions = IonElementLoaderOptions()) =
    IonElementLoaderImpl(options)

/** Provides syntactically lighter way of invoking [IonElementLoader.loadSingleElement]. */
fun loadSingleElement(ionText: String, options: IonElementLoaderOptions = IonElementLoaderOptions()) =
    createIonElementLoader(options).loadSingleElement(ionText)

/** Provides syntactically lighter method of invoking [IonElementLoader.loadSingleElement]. */
fun loadSingleElement(ionReader: IonReader, options: IonElementLoaderOptions = IonElementLoaderOptions()) =
    createIonElementLoader(options).loadSingleElement(ionReader)

/** Provides syntactically lighter method of invoking [IonElementLoader.loadAllElements]. */
fun loadAllElements(ionText: String, options: IonElementLoaderOptions = IonElementLoaderOptions()): Iterable<AnyElement> =
    createIonElementLoader(options).loadAllElements(ionText)

/** Provides syntactically lighter method of invoking [IonElementLoader.loadAllElements]. */
fun loadAllElements(ionReader: IonReader, options: IonElementLoaderOptions = IonElementLoaderOptions()): Iterable<AnyElement> =
    createIonElementLoader(options).loadAllElements(ionReader)

/** Provides syntactically lighter method of invoking [IonElementLoader.loadAllElements]. */
fun loadCurrentElement(ionReader: IonReader, options: IonElementLoaderOptions = IonElementLoaderOptions()): AnyElement =
    createIonElementLoader(options).loadCurrentElement(ionReader)
