/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.protocol;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hdfs.server.protocol.BlockMetaDataInfo;
import org.apache.hadoop.ipc.VersionedProtocol;

/** An client-datanode protocol for block recovery
 */
public interface ClientDatanodeProtocol extends VersionedProtocol {
  public static final Log LOG = LogFactory.getLog(ClientDatanodeProtocol.class);

  /**
   * 2: recoverBlock returns the datanodes on which recovery was successful.
   */
  public static final long versionID = 2L;

  /** Start generation-stamp recovery for specified block
   * @param block the specified block
   * @param targets the list of possible locations of specified block
   * @return the new blockid if recovery successful and the generation stamp
   * got updated as part of the recovery, else returns null if the block id
   * not have any data and the block was deleted.
   * @throws IOException
   */
  LocatedBlock recoverBlock(Block block, DatanodeInfo[] targets) throws IOException;
  
  
  //
  // The following is added for client initiated recovery
  // (taken from InterDatanodeProtocol)
  
  /** @return the BlockMetaDataInfo of a block;
   *  null if the block is not found 
   */
  BlockMetaDataInfo getBlockMetaDataInfo(Block block) throws IOException;
  /**
   * Update the block to the new generation stamp and length.  
   */
  void updateBlock(Block oldblock, Block newblock, boolean finalize) throws IOException;
}
