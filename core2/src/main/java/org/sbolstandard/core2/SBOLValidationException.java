/*
 * Copyright (c) 2012 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sbolstandard.core2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sbolstandard.core2.abstract_classes.Identified;

/**
 * Exception throw during validation of an SBOL document.
 * 
 * @author Evren Sirin
 * @version 2.0
 */
public class SBOLValidationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<Identified> objects;

//	/**
//	 * Creates a new exception instance with the given message and objects causing the problem.
//	 */
//	public SBOLValidationException(String message, Identified... objects) {
//		this(message, Arrays.asList(objects));
//	}

//	/**
//	 * Creates a new exception instance with the given message and objects causing the problem.
//	 */
//	public SBOLValidationException(String message, java.util.Collection<? extends Identified> objects) {
//		super(formatMessage(message, objects));
//
//		this.objects = Collections.unmodifiableList(new ArrayList<Identified>(objects));
//	}

    public SBOLValidationException(String message, Throwable cause, Identified ... objects) {
        super(message, cause);
        this.objects = Collections.unmodifiableList(Arrays.asList(objects));
    }

    /**
	 * Creates a new exception instance with the given cause but no specific objects for the problem.
	 */
	public SBOLValidationException(Throwable cause) {
		super(cause);

		this.objects = Collections.emptyList();
	}

	/**
	 * Returns the list of objects relevant for the validation exception. This list may be empty if the exact object
	 * for the validation exception is not known. In those cases, the {@link #getCause() cause} of the exception can 
	 * provide more information.
	 */
	public java.util.Collection<Identified> getObjects() {
		return objects;
	}

//	private static String formatMessage(String message, java.util.Collection<? extends Identified> objects) {
//		final StringBuilder sb = new StringBuilder(message);
//		if (!objects.isEmpty()) {
//			sb.append(": ");
//			boolean first = true;
//			for (Identified obj : objects) {
//				if (first) {
//					first = false;
//				}
//				else {
//					sb.append(", ");
//				}
//				if (obj.getURI() != null) {
//					sb.append(obj.getURI());
//				}
//			}
//		}
//		return sb.toString();
//	}
}
