package com.smartbrowser.adblocker;

import com.smartbrowser.utils.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RuleLoader {
    
    /**
     * 从classpath资源文件加载规则
     * 
     * @param filepath 相对于resources的路径，如 "/adblock/easylist.txt"
     * @return 解析后的规则列表
     */
    public List<FilterRule> loadFromFile(String filepath) {
        List<FilterRule> rules = new ArrayList<>();
        
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            if (is == null) {
                Logger.warn("规则文件不存在: {}", filepath);
                return rules;
            }
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
            );
            
            String line;
            int lineNum = 0;
            int validCount = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNum++;
                
                // 跳过注释和空行
                if (line.trim().isEmpty() || line.startsWith("!")) {
                    continue;
                }
                
                // 解析规则
                FilterRule rule = FilterRule.parse(line);
                if (rule != null) {
                    rules.add(rule);
                    validCount++;
                }
            }
            
            reader.close();
            Logger.info("加载规则文件: {} - 总行数: {}, 有效规则: {}", 
                        filepath, lineNum, validCount);
            
        } catch (Exception e) {
            Logger.error("加载规则文件失败: " + filepath, e);
        }
        
        return rules;
    }
    
    /**
     * 解析规则文本内容
     */
    public List<FilterRule> parseRules(String content) {
        List<FilterRule> rules = new ArrayList<>();
        
        if (content == null || content.isEmpty()) {
            return rules;
        }
        
        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            
            // 跳过注释和空行
            if (line.isEmpty() || line.startsWith("!")) {
                continue;
            }
            
            FilterRule rule = FilterRule.parse(line);
            if (rule != null) {
                rules.add(rule);
            }
        }
        
        return rules;
    }
}
