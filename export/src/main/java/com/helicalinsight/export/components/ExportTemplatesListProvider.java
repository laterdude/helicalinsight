/**
 *    Copyright (C) 2013-2017 Helical IT Solutions (http://www.helicalinsight.com) - All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.helicalinsight.export.components;

import com.helicalinsight.efw.serviceframework.IComponent;
import com.helicalinsight.export.ExportUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Collection;

/**
 * Created by Author on 08/12/2016
 *
 * @author Somen
 */
@SuppressWarnings("unused")
public class ExportTemplatesListProvider implements IComponent {

    @Override
    public boolean isThreadSafeToCache() {
        return true;
    }


    @Override
    public String executeComponent(String jsonFormData) {
        String templatesDirectory = ExportUtils.getTemplatesDirectory();
        File folder = new File(templatesDirectory);
        String extension[] = {ExportUtils.JSON_EXTENSION.substring(1)};
        Collection<File> files = FileUtils.listFiles(folder, extension, true);
        JSONObject response = new JSONObject();
        JSONArray templates = new JSONArray();
        for (File file : files) {
            String jsonContent = ExportUtils.getFileAsString(file.getPath());
            JSONObject fileAsJson = JSONObject.fromObject(jsonContent).discard("execute");
            fileAsJson.put("templateId", FilenameUtils.removeExtension(file.getName()));
            templates.add(fileAsJson);
        }
        response.accumulate("templates", templates);
        return response.toString();
    }


}