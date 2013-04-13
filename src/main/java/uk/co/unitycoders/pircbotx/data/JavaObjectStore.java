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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Java native object serialisation.
 * 
 * The simplest form of persistent storage is serialisation. This class uses the
 * in built serialisation system to create files on the system for classes which
 * need to persist over many runs of the bot.
 */
public class JavaObjectStore implements ObjectStorage
{
	private final File basePath;

	/**
	 * Creates a new JavaObjectStore.
	 * 
	 * @param pathStr the base path for files read/written by this store
	 * @return the new JavaObjectStore.
	 */
	public static ObjectStorage build(String pathStr)
	{
		File path = new File(pathStr);

		// if the path doesn't exist, create it
		if (!path.exists())
		{
			path.mkdir();
		}

		return new JavaObjectStore(path);
	}

	/**
	 * Creates a new JavaObjectStore.
	 * 
	 * @param basePath the base path for files read/written by this store
	 */
	public JavaObjectStore(File basePath)
	{
		this.basePath = basePath;
	}

	public void store(String name, Serializable object) throws IOException
	{
		File path = new File(basePath, name + ".obj");
		if (path.exists())
		{
			path.delete();
		}
		path.createNewFile();

		FileOutputStream fos = new FileOutputStream(path, true);
		ObjectOutputStream out = new ObjectOutputStream(fos);

		out.writeObject(object);

		out.close();
		fos.close();
	}

	public Object load(String name) throws IOException, ClassNotFoundException
	{
		File path = new File(basePath, name + ".obj");
		FileInputStream fin = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fin);

		Object oin = in.readObject();

		fin.close();
		in.close();

		return oin;
	}

}
