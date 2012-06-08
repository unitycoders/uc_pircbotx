/**
 * Copyright © 2012 Bruce Cowan <bruce@bcowan.me.uk>
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
 * java spec. states that such an object implement Serialisable.
 */
public interface ObjectStorage {

    public void store(String name, Serializable object) throws IOException;
    public Object load(String name) throws IOException, ClassNotFoundException;
}
