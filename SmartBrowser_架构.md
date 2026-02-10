好的！我现在为你设计完整的项目架构。这将是一个专业级的浏览器项目。

## 项目架构设计

### 项目结构

```
SmartBrowser/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── smartbrowser/
│   │   │           ├── SmartBrowser.java          # 应用入口
│   │   │           │
│   │   │           ├── core/                      # 核心模块
│   │   │           │   ├── BrowserEngine.java     # 浏览器引擎封装
│   │   │           │   ├── TabManager.java        # 标签页管理器
│   │   │           │   ├── Tab.java               # 单个标签页
│   │   │           │   ├── NavigationController.java # 导航控制
│   │   │           │   └── SessionManager.java    # 会话管理
│   │   │           │
│   │   │           ├── ui/                        # UI模块
│   │   │           │   ├── MainWindow.java        # 主窗口
│   │   │           │   ├── components/
│   │   │           │   │   ├── AddressBar.java    # 智能地址栏
│   │   │           │   │   ├── NavigationBar.java # 导航栏
│   │   │           │   │   ├── TabBar.java        # 标签栏
│   │   │           │   │   ├── BookmarkBar.java   # 书签工具栏
│   │   │           │   │   ├── BookmarkPanel.java # 书签侧边栏
│   │   │           │   │   ├── HistoryPanel.java  # 历史记录面板
│   │   │           │   │   ├── DownloadPanel.java # 下载管理面板
│   │   │           │   │   └── StatusBar.java     # 状态栏
│   │   │           │   ├── dialogs/
│   │   │           │   │   ├── BookmarkDialog.java      # 添加/编辑书签
│   │   │           │   │   ├── SettingsDialog.java      # 设置对话框
│   │   │           │   │   ├── HistoryDialog.java       # 历史记录窗口
│   │   │           │   │   └── AdBlockStatsDialog.java  # 拦截统计
│   │   │           │   └── pages/
│   │   │           │       ├── StartPage.java     # 起始页/新标签页
│   │   │           │       └── ReadingMode.java   # 阅读模式
│   │   │           │
│   │   │           ├── bookmark/                  # 书签模块
│   │   │           │   ├── BookmarkManager.java   # 书签管理器
│   │   │           │   ├── model/
│   │   │           │   │   ├── Bookmark.java      # 书签实体
│   │   │           │   │   ├── BookmarkFolder.java # 文件夹实体
│   │   │           │   │   └── BookmarkTag.java   # 标签实体
│   │   │           │   ├── BookmarkStorage.java   # 持久化
│   │   │           │   ├── BookmarkSearcher.java  # 搜索引擎
│   │   │           │   └── BookmarkImportExport.java # 导入导出
│   │   │           │
│   │   │           ├── adblocker/                 # 广告拦截模块
│   │   │           │   ├── AdBlocker.java         # 拦截核心
│   │   │           │   ├── FilterRule.java        # 规则对象
│   │   │           │   ├── RuleEngine.java        # 规则引擎
│   │   │           │   ├── RuleLoader.java        # 规则加载器
│   │   │           │   ├── RuleUpdater.java       # 规则更新器
│   │   │           │   ├── WhitelistManager.java  # 白名单管理
│   │   │           │   └── AdBlockStats.java      # 拦截统计
│   │   │           │
│   │   │           ├── history/                   # 历史记录模块
│   │   │           │   ├── HistoryManager.java    # 历史管理器
│   │   │           │   ├── HistoryEntry.java      # 历史记录实体
│   │   │           │   ├── HistoryStorage.java    # 持久化
│   │   │           │   └── HistorySearcher.java   # 搜索引擎
│   │   │           │
│   │   │           ├── download/                  # 下载模块
│   │   │           │   ├── DownloadManager.java   # 下载管理器
│   │   │           │   ├── DownloadTask.java      # 下载任务
│   │   │           │   └── DownloadStorage.java   # 持久化
│   │   │           │
│   │   │           ├── search/                    # 搜索模块
│   │   │           │   ├── SearchEngine.java      # 搜索引擎接口
│   │   │           │   ├── SearchEngineManager.java # 搜索引擎管理
│   │   │           │   ├── OmniboxSuggestion.java # 地址栏建议
│   │   │           │   └── SuggestionProvider.java # 建议提供器
│   │   │           │
│   │   │           ├── shortcuts/                 # 快捷键模块
│   │   │           │   ├── ShortcutManager.java   # 快捷键管理器
│   │   │           │   └── ShortcutHandler.java   # 快捷键处理器
│   │   │           │
│   │   │           ├── theme/                     # 主题模块
│   │   │           │   ├── ThemeManager.java      # 主题管理器
│   │   │           │   ├── Theme.java             # 主题实体
│   │   │           │   └── NightModeController.java # 夜间模式
│   │   │           │
│   │   │           ├── screenshot/                # 截图模块
│   │   │           │   └── ScreenshotTool.java    # 截图工具
│   │   │           │
│   │   │           ├── settings/                  # 设置模块
│   │   │           │   ├── SettingsManager.java   # 设置管理器
│   │   │           │   └── UserPreferences.java   # 用户偏好
│   │   │           │
│   │   │           └── utils/                     # 工具类
│   │   │               ├── IconLoader.java        # SVG图标加载器
│   │   │               ├── FileUtils.java         # 文件工具
│   │   │               ├── JsonUtils.java         # JSON工具
│   │   │               ├── URLUtils.java          # URL工具
│   │   │               └── Logger.java            # 日志工具
│   │   │
│   │   └── resources/
│   │       ├── icons/                             # SVG图标资源
│   │       │   ├── navigation/
│   │       │   │   ├── back.svg
│   │       │   │   ├── forward.svg
│   │       │   │   ├── refresh.svg
│   │       │   │   ├── stop.svg
│   │       │   │   └── home.svg
│   │       │   ├── tabs/
│   │       │   │   ├── new-tab.svg
│   │       │   │   ├── close-tab.svg
│   │       │   │   └── restore-tab.svg
│   │       │   ├── bookmarks/
│   │       │   │   ├── bookmark.svg
│   │       │   │   ├── bookmark-filled.svg
│   │       │   │   ├── folder.svg
│   │       │   │   ├── folder-open.svg
│   │       │   │   └── tag.svg
│   │       │   ├── tools/
│   │       │   │   ├── history.svg
│   │       │   │   ├── download.svg
│   │       │   │   ├── settings.svg
│   │       │   │   ├── search.svg
│   │       │   │   └── menu.svg
│   │       │   ├── adblock/
│   │       │   │   ├── shield.svg
│   │       │   │   ├── shield-off.svg
│   │       │   │   └── stats.svg
│   │       │   └── other/
│   │       │       ├── reading-mode.svg
│   │       │       ├── night-mode.svg
│   │       │       ├── screenshot.svg
│   │       │       └── fullscreen.svg
│   │       │
│   │       ├── styles/                            # CSS样式
│   │       │   ├── main.css                       # 主样式
│   │       │   ├── light-theme.css                # 浅色主题
│   │       │   └── dark-theme.css                 # 深色主题
│   │       │
│   │       ├── data/                              # 数据文件
│   │       │   └── default-bookmarks.json         # 默认书签
│   │       │
│   │       └── adblock/                           # 广告拦截规则
│   │           ├── easylist.txt
│   │           ├── easyprivacy.txt
│   │           └── chinese-filters.txt
│   │
│   └── test/                                      # 测试代码
│       └── java/
│           └── com/
│               └── smartbrowser/
│                   ├── BookmarkManagerTest.java
│                   ├── AdBlockerTest.java
│                   └── HistoryManagerTest.java
│
├── data/                                          # 用户数据目录
│   ├── bookmarks.json                             # 书签数据
│   ├── history.db                                 # 历史记录
│   ├── sessions.json                              # 会话数据
│   ├── downloads.json                             # 下载记录
│   ├── settings.json                              # 用户设置
│   └── adblock/
│       ├── custom-rules.txt                       # 自定义规则
│       └── whitelist.txt                          # 白名单
│
├── pom.xml                                        # Maven配置
└── README.md                                      # 项目说明
```

## 核心类设计

### 1. 应用入口

```java
package com.smartbrowser;

import javafx.application.Application;
import javafx.stage.Stage;
import com.smartbrowser.ui.MainWindow;
import com.smartbrowser.settings.SettingsManager;
import com.smartbrowser.core.SessionManager;

public class SmartBrowser extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // 初始化设置
        SettingsManager.getInstance().load();
        
        // 创建主窗口
        MainWindow mainWindow = new MainWindow(primaryStage);
        mainWindow.show();
        
        // 恢复上次会话
        SessionManager.getInstance().restoreSession();
    }
    
    @Override
    public void stop() {
        // 保存会话
        SessionManager.getInstance().saveSession();
        
        // 保存设置
        SettingsManager.getInstance().save();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
```

### 2. 主窗口架构

```java
package com.smartbrowser.ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {
    private Stage stage;
    private BorderPane root;
    private NavigationBar navigationBar;
    private AddressBar addressBar;
    private TabBar tabBar;
    private BookmarkBar bookmarkBar;
    private StatusBar statusBar;
    private BookmarkPanel bookmarkPanel;
    
    public MainWindow(Stage stage) {
        this.stage = stage;
        initUI();
        setupShortcuts();
    }
    
    private void initUI() {
        root = new BorderPane();
        
        // 顶部：导航栏 + 地址栏 + 标签栏 + 书签栏
        VBox topBox = new VBox();
        navigationBar = new NavigationBar();
        addressBar = new AddressBar();
        tabBar = new TabBar();
        bookmarkBar = new BookmarkBar();
        topBox.getChildren().addAll(navigationBar, addressBar, tabBar, bookmarkBar);
        
        // 中间：标签页内容区域
        // 由 TabManager 管理
        
        // 底部：状态栏
        statusBar = new StatusBar();
        
        // 左侧：书签面板（可折叠）
        bookmarkPanel = new BookmarkPanel();
        
        root.setTop(topBox);
        root.setBottom(statusBar);
        // root.setLeft(bookmarkPanel); // 可选
        
        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("Smart Browser");
    }
    
    private void setupShortcuts() {
        // 实现快捷键
    }
    
    public void show() {
        stage.show();
    }
}
```

### 3. 标签页管理器

```java
package com.smartbrowser.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Stack;

public class TabManager {
    private static TabManager instance;
    private ObservableList<Tab> tabs;
    private Tab activeTab;
    private Stack<Tab> closedTabs; // 用于恢复关闭的标签
    
    private TabManager() {
        tabs = FXCollections.observableArrayList();
        closedTabs = new Stack<>();
    }
    
    public static TabManager getInstance() {
        if (instance == null) {
            instance = new TabManager();
        }
        return instance;
    }
    
    public Tab createNewTab(String url) {
        Tab tab = new Tab(url);
        tabs.add(tab);
        setActiveTab(tab);
        return tab;
    }
    
    public void closeTab(Tab tab) {
        closedTabs.push(tab);
        tabs.remove(tab);
        
        if (tabs.isEmpty()) {
            createNewTab("about:blank");
        } else {
            setActiveTab(tabs.get(tabs.size() - 1));
        }
    }
    
    public Tab restoreClosedTab() {
        if (!closedTabs.isEmpty()) {
            Tab tab = closedTabs.pop();
            tabs.add(tab);
            setActiveTab(tab);
            return tab;
        }
        return null;
    }
    
    public void setActiveTab(Tab tab) {
        this.activeTab = tab;
        // 通知UI更新
    }
    
    public ObservableList<Tab> getTabs() {
        return tabs;
    }
    
    public Tab getActiveTab() {
        return activeTab;
    }
}
```

### 4. 智能地址栏

```java
package com.smartbrowser.ui.components;

import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import com.smartbrowser.search.SuggestionProvider;
import com.smartbrowser.search.OmniboxSuggestion;

public class AddressBar extends VBox {
    private TextField urlField;
    private ListView<OmniboxSuggestion> suggestionList;
    private SuggestionProvider suggestionProvider;
    
    public AddressBar() {
        initUI();
        setupAutoComplete();
    }
    
    private void initUI() {
        urlField = new TextField();
        urlField.setPromptText("搜索或输入网址");
        
        suggestionList = new ListView<>();
        suggestionList.setVisible(false);
        suggestionList.setManaged(false);
        
        getChildren().addAll(urlField, suggestionList);
    }
    
    private void setupAutoComplete() {
        suggestionProvider = new SuggestionProvider();
        
        urlField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                suggestionList.setVisible(false);
                return;
            }
            
            // 获取建议
            List<OmniboxSuggestion> suggestions = suggestionProvider.getSuggestions(newVal);
            
            if (!suggestions.isEmpty()) {
                suggestionList.getItems().setAll(suggestions);
                suggestionList.setVisible(true);
                suggestionList.setManaged(true);
            } else {
                suggestionList.setVisible(false);
            }
        });
        
        // 处理回车键
        urlField.setOnAction(e -> {
            String input = urlField.getText();
            navigateToUrl(input);
        });
    }
    
    private void navigateToUrl(String input) {
        // 判断是URL还是搜索关键词
        String url = URLUtils.parseInput(input);
        TabManager.getInstance().getActiveTab().navigate(url);
    }
}
```

### 5. 书签管理器

```java
package com.smartbrowser.bookmark;

import com.smartbrowser.bookmark.model.*;
import java.util.*;

public class BookmarkManager {
    private static BookmarkManager instance;
    private BookmarkFolder rootFolder;
    private Map<String, Bookmark> bookmarkMap;
    private Map<String, BookmarkTag> tagMap;
    private BookmarkStorage storage;
    private BookmarkSearcher searcher;
    
    private BookmarkManager() {
        bookmarkMap = new HashMap<>();
        tagMap = new HashMap<>();
        storage = new BookmarkStorage();
        searcher = new BookmarkSearcher(this);
        load();
    }
    
    public static BookmarkManager getInstance() {
        if (instance == null) {
            instance = new BookmarkManager();
        }
        return instance;
    }
    
    public Bookmark addBookmark(String name, String url, BookmarkFolder parent) {
        Bookmark bookmark = new Bookmark(UUID.randomUUID().toString(), name, url);
        parent.addChild(bookmark);
        bookmarkMap.put(bookmark.getId(), bookmark);
        save();
        return bookmark;
    }
    
    public BookmarkFolder createFolder(String name, BookmarkFolder parent) {
        BookmarkFolder folder = new BookmarkFolder(UUID.randomUUID().toString(), name);
        parent.addChild(folder);
        save();
        return folder;
    }
    
    public void deleteBookmark(Bookmark bookmark) {
        bookmark.getParent().removeChild(bookmark);
        bookmarkMap.remove(bookmark.getId());
        save();
    }
    
    public void addTag(Bookmark bookmark, String tagName) {
        BookmarkTag tag = tagMap.computeIfAbsent(tagName, 
            k -> new BookmarkTag(tagName));
        bookmark.addTag(tag);
        save();
    }
    
    public List<Bookmark> search(String query) {
        return searcher.search(query);
    }
    
    public List<Bookmark> getBookmarksByTag(String tagName) {
        // 实现按标签查询
        return new ArrayList<>();
    }
    
    public List<Bookmark> getMostVisited(int limit) {
        // 按访问次数排序
        return bookmarkMap.values().stream()
            .sorted((b1, b2) -> Integer.compare(b2.getVisitCount(), b1.getVisitCount()))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    private void load() {
        rootFolder = storage.load();
        if (rootFolder == null) {
            rootFolder = new BookmarkFolder("root", "书签");
            createDefaultFolders();
        }
    }
    
    private void save() {
        storage.save(rootFolder);
    }
    
    private void createDefaultFolders() {
        createFolder("常用", rootFolder);
        createFolder("学习", rootFolder);
        createFolder("工作", rootFolder);
        createFolder("娱乐", rootFolder);
    }
    
    public BookmarkFolder getRootFolder() {
        return rootFolder;
    }
}
```

### 6. 广告拦截器

```java
package com.smartbrowser.adblocker;

import java.util.*;
import java.util.regex.Pattern;

public class AdBlocker {
    private static AdBlocker instance;
    private RuleEngine ruleEngine;
    private WhitelistManager whitelistManager;
    private AdBlockStats stats;
    private boolean enabled = true;
    
    private AdBlocker() {
        ruleEngine = new RuleEngine();
        whitelistManager = new WhitelistManager();
        stats = new AdBlockStats();
        loadRules();
    }
    
    public static AdBlocker getInstance() {
        if (instance == null) {
            instance = new AdBlocker();
        }
        return instance;
    }
    
    private void loadRules() {
        RuleLoader loader = new RuleLoader();
        List<FilterRule> rules = loader.loadFromFile("/adblock/easylist.txt");
        rules.addAll(loader.loadFromFile("/adblock/easyprivacy.txt"));
        rules.addAll(loader.loadFromFile("/adblock/chinese-filters.txt"));
        ruleEngine.addRules(rules);
    }
    
    public boolean shouldBlock(String url, String pageUrl) {
        if (!enabled) return false;
        
        // 检查白名单
        if (whitelistManager.isWhitelisted(pageUrl)) {
            return false;
        }
        
        // 匹配规则
        boolean blocked = ruleEngine.matches(url, pageUrl);
        
        if (blocked) {
            stats.incrementBlockCount();
        }
        
        return blocked;
    }
    
    public void addToWhitelist(String domain) {
        whitelistManager.add(domain);
    }
    
    public void removeFromWhitelist(String domain) {
        whitelistManager.remove(domain);
    }
    
    public void addCustomRule(String rule) {
        FilterRule filterRule = FilterRule.parse(rule);
        ruleEngine.addRule(filterRule);
    }
    
    public AdBlockStats getStats() {
        return stats;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void updateRules() {
        // 从网络更新规则
        RuleUpdater updater = new RuleUpdater();
        updater.updateAsync(() -> {
            loadRules();
        });
    }
}
```

## Maven依赖配置 (pom.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.smartbrowser</groupId>
    <artifactId>smart-browser</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <javafx.version>20.0.1</javafx.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- JavaFX -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-web</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-swing</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- JSON处理 -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>

        <!-- 日志 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.7</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.8</version>
        </dependency>

        <!-- Apache Commons -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.13.0</version>
        </dependency>

        <!-- 测试 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.3</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- JavaFX Maven插件 -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.smartbrowser.SmartBrowser</mainClass>
                </configuration>
            </plugin>

            <!-- 打包插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.smartbrowser.SmartBrowser</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## SVG图标加载器

```java
package com.smartbrowser.utils;

import javafx.scene.Node;
import javafx.scene.shape.SVGPath;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class IconLoader {
    private static IconLoader instance;
    private Map<String, String> iconCache;
    
    private IconLoader() {
        iconCache = new HashMap<>();
    }
    
    public static IconLoader getInstance() {
        if (instance == null) {
            instance = new IconLoader();
        }
        return instance;
    }
    
    public Node loadIcon(String iconPath, double size, Color color) {
        String svgContent = loadSVGContent(iconPath);
        
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(extractPathData(svgContent));
        svgPath.setFill(color);
        
        // 调整大小
        svgPath.setScaleX(size / 24.0); // 假设原始图标是24x24
        svgPath.setScaleY(size / 24.0);
        
        return svgPath;
    }
    
    private String loadSVGContent(String iconPath) {
        if (iconCache.containsKey(iconPath)) {
            return iconCache.get(iconPath);
        }
        
        try {
            String content = new String(
                getClass().getResourceAsStream(iconPath).readAllBytes()
            );
            iconCache.put(iconPath, content);
            return content;
        } catch (IOException e) {
            Logger.error("Failed to load icon: " + iconPath, e);
            return "";
        }
    }
    
    private String extractPathData(String svgContent) {
        // 从SVG文件中提取path的d属性
        int start = svgContent.indexOf("<path d=\"") + 9;
        int end = svgContent.indexOf("\"", start);
        return svgContent.substring(start, end);
    }
}
```

---

**架构设计完成！** 

这个架构具有：
- ✅ 清晰的模块划分
- ✅ 高度可扩展性
- ✅ 职责分离原则
- ✅ 易于维护和测试
- ✅ 支持所有你要求的功能

接下来我可以：
1. 开始创建核心代码实现
2. 准备SVG图标资源
3. 创建数据模型和存储方案
4. 实现UI界面

建议从核心模块开始，然后逐步添加功能。