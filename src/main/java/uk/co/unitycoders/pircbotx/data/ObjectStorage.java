/**
 * Copyright © 2012 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with uc_PircBotX.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.data;

import java.io.IOException;
import java.io.Serializable;

/**
 * Interface for storing objects.
 *
 * Java objects are too complex to be stored as-is in a relational database.
 * They can however be stored using serialisation. It's a requirement of the
 * java spec. states that such an object implement {@link Serializable}.
 */
public interface ObjectStorage
{
	/**
	 * Store an object in a file.
	 *
	 * @param name the filename without extension to store the object in
	 * @param object the object to store
	 * @throws IOException if an I/O error occurred
	 */
	public void store(String name, Serializable object) throws IOException;

	/**
	 * Load an object from a file.
	 *
	 * @param name the filename without extension to load from
	 * @return the new object loaded from the file
	 * @throws IOException if an I/O error occurred
	 * @throws ClassNotFoundException if the class of the object can't be found
	 */
	public Object load(String name) throws IOException, ClassNotFoundException;
}
