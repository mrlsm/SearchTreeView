package com.mrlsm.searchtreeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mrlsm.searchtreeview.adapter.TreeNodeBinder;
import com.mrlsm.searchtreeview.adapter.TreeViewAdapter;
import com.mrlsm.searchtreeview.model.TreeNode;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycle;
    private EditText mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = findViewById(R.id.search_edit);
        mRecycle = findViewById(R.id.recycler_view);

        initView(jsonToList(mJson));
    }

    public static List<TreeNode> jsonToList(String json) {
        List<TreeNode> list = null;
        try {
            Type type = new TypeToken<List<TreeNode>>() {
            }.getType();
            list = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void initView(List<TreeNode> data) {
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        TreeNodeBinder nodeBinder = new TreeNodeBinder();
        TreeViewAdapter treeAdapter = new TreeViewAdapter<>(data, nodeBinder);
        treeAdapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    if (treeAdapter.isCanExpand(node)) {
                        onToggle(!node.isExpand(), holder);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "click " + node.getName(), Toast.LENGTH_SHORT);
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                TreeNodeBinder.ViewHolder viewHolder = (TreeNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = viewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree).start();
            }
        });
        mRecycle.setAdapter(treeAdapter);

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                nodeBinder.setKeyword(keyword);
                expandTreeNode(data);
                treeAdapter.refresh(data, keyword);
            }
        });
    }

    private synchronized void expandTreeNode(List<TreeNode> treeNodes) {
        if (treeNodes == null || treeNodes.size() < 1) {
            return;
        }
        for (TreeNode node : treeNodes) {
            if (!node.isLeaf()) {
                expandTreeNode(node.getChildren());
                node.expand();
            }
        }
    }

    private final static String mJson = "[\n" +
            "    {\n" +
            "      \"id\": \"682270541196963840\",\n" +
            "      \"name\": \"01-父节点\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": \"682272215244353536\",\n" +
            "          \"name\": \"03-01-父节点\",\n" +
            "          \"parentId\": \"682270541196963840\",\n" +
            "          \"level\": 2,\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"682547281702039552\",\n" +
            "              \"name\": \"03-01\",\n" +
            "              \"parentId\": \"682272215244353536\",\n" +
            "              \"level\": 3,\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"682547629422424064\",\n" +
            "                  \"name\": \"0301010001-Q5\",\n" +
            "                  \"parentId\": \"682547281702039552\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0001\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"工业\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0301010001\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682548739876990976\",\n" +
            "                  \"name\": \"0301010002-w2\",\n" +
            "                  \"parentId\": \"682547281702039552\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0002\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0301010002\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682548853211279360\",\n" +
            "                  \"name\": \"0301010003-NA\",\n" +
            "                  \"parentId\": \"682547281702039552\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0003\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0301010003\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682549455483973632\",\n" +
            "                  \"name\": \"0301010004-B\",\n" +
            "                  \"parentId\": \"682547281702039552\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0004\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0301010004\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"697488717983657984\",\n" +
            "                  \"name\": \"03010105-D\",\n" +
            "                  \"parentId\": \"682547281702039552\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"05\",\n" +
            "                  \"codeName\": null,\n" +
            "                  \"strength\": \"50kg/件\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"03010105\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"leaf\": false,\n" +
            "              \"medicineName\": \"01\",\n" +
            "              \"code\": \"01\",\n" +
            "              \"codeName\": null,\n" +
            "              \"strength\": null,\n" +
            "              \"packageSize\": null,\n" +
            "              \"unit\": null,\n" +
            "              \"fullCode\": \"030101\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"leaf\": false,\n" +
            "          \"code\": \"01\",\n" +
            "          \"codeName\": null,\n" +
            "          \"strength\": null,\n" +
            "          \"packageSize\": null,\n" +
            "          \"unit\": null,\n" +
            "          \"fullCode\": \"0301\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"682272291408719872\",\n" +
            "          \"name\": \"02-花花\",\n" +
            "          \"parentId\": \"682270541196963840\",\n" +
            "          \"level\": 2,\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"682548911650516992\",\n" +
            "              \"name\": \"01-01\",\n" +
            "              \"parentId\": \"682272291408719872\",\n" +
            "              \"level\": 3,\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"682549005124775936\",\n" +
            "                  \"name\": \"0302010001-AA\",\n" +
            "                  \"parentId\": \"682548911650516992\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0001\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0302010001\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"leaf\": false,\n" +
            "              \"medicineName\": \"01\",\n" +
            "              \"code\": \"01\",\n" +
            "              \"codeName\": null,\n" +
            "              \"strength\": null,\n" +
            "              \"packageSize\": null,\n" +
            "              \"unit\": null,\n" +
            "              \"fullCode\": \"030201\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"leaf\": false,\n" +
            "          \"code\": \"02\",\n" +
            "          \"codeName\": null,\n" +
            "          \"strength\": null,\n" +
            "          \"packageSize\": null,\n" +
            "          \"unit\": null,\n" +
            "          \"fullCode\": \"0302\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"682272841659461632\",\n" +
            "          \"name\": \"05-BB\",\n" +
            "          \"parentId\": \"682270541196963840\",\n" +
            "          \"level\": 2,\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"682547752621715456\",\n" +
            "              \"name\": \"01-01\",\n" +
            "              \"parentId\": \"682272841659461632\",\n" +
            "              \"level\": 3,\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"682547899531407360\",\n" +
            "                  \"name\": \"0305010001-CC\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0001\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010001\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682548309306519552\",\n" +
            "                  \"name\": \"0305010002-DD\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0002\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010002\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682548500940075008\",\n" +
            "                  \"name\": \"0305010003-5%EE\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0003\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010003\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682548591558012928\",\n" +
            "                  \"name\": \"0305010004-FF\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"medicineName\": \"吡啶\",\n" +
            "                  \"code\": \"0004\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"CP\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010004\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682549580340015104\",\n" +
            "                  \"name\": \"0305010005-GG\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0005\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010005\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682549675965952000\",\n" +
            "                  \"name\": \"0305010006-WW\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0006\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"工业\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010006\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682549804043218944\",\n" +
            "                  \"name\": \"0305010007-SS\",\n" +
            "                  \"parentId\": \"682547752621715456\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0007\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"AR\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0305010007\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"leaf\": false,\n" +
            "              \"code\": \"01\",\n" +
            "              \"codeName\": null,\n" +
            "              \"strength\": null,\n" +
            "              \"packageSize\": null,\n" +
            "              \"unit\": null,\n" +
            "              \"fullCode\": \"030501\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"leaf\": false,\n" +
            "          \"code\": \"05\",\n" +
            "          \"codeName\": null,\n" +
            "          \"strength\": null,\n" +
            "          \"packageSize\": null,\n" +
            "          \"unit\": null,\n" +
            "          \"fullCode\": \"0305\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"leaf\": false,\n" +
            "      \"code\": \"03\",\n" +
            "      \"codeName\": null,\n" +
            "      \"strength\": null,\n" +
            "      \"packageSize\": null,\n" +
            "      \"unit\": null,\n" +
            "      \"fullCode\": \"03\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"682270597228670976\",\n" +
            "      \"name\": \"06-哈哈\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": \"682273923244634112\",\n" +
            "          \"name\": \"01-嘿嘿\",\n" +
            "          \"parentId\": \"682270597228670976\",\n" +
            "          \"level\": 2,\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"682553829841055744\",\n" +
            "              \"name\": \"01-01\",\n" +
            "              \"parentId\": \"682273923244634112\",\n" +
            "              \"level\": 3,\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"682554050658578432\",\n" +
            "                  \"name\": \"0601010001-AA\",\n" +
            "                  \"parentId\": \"682553829841055744\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0001\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"0.08*600*900\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0601010001\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682554191050321920\",\n" +
            "                  \"name\": \"0601010002-BB\",\n" +
            "                  \"parentId\": \"682553829841055744\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0002\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"0.08*600*900\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0601010002\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"leaf\": false,\n" +
            "              \"medicineName\": \"01\",\n" +
            "              \"code\": \"01\",\n" +
            "              \"codeName\": null,\n" +
            "              \"strength\": null,\n" +
            "              \"packageSize\": null,\n" +
            "              \"unit\": null,\n" +
            "              \"fullCode\": \"060101\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"leaf\": false,\n" +
            "          \"code\": \"01\",\n" +
            "          \"codeName\": null,\n" +
            "          \"strength\": null,\n" +
            "          \"packageSize\": null,\n" +
            "          \"unit\": null,\n" +
            "          \"fullCode\": \"0601\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"leaf\": false,\n" +
            "      \"code\": \"06\",\n" +
            "      \"codeName\": null,\n" +
            "      \"strength\": null,\n" +
            "      \"packageSize\": null,\n" +
            "      \"unit\": null,\n" +
            "      \"fullCode\": \"06\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"682270838082383872\",\n" +
            "      \"name\": \"07-生菜\",\n" +
            "      \"parentId\": \"-1\",\n" +
            "      \"level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": \"682271110418542592\",\n" +
            "          \"name\": \"01-DD\",\n" +
            "          \"parentId\": \"682270838082383872\",\n" +
            "          \"level\": 2,\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"682554293965959168\",\n" +
            "              \"name\": \"01-01-DD\",\n" +
            "              \"parentId\": \"682271110418542592\",\n" +
            "              \"level\": 3,\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"682554388769812480\",\n" +
            "                  \"name\": \"0701010001-DD\",\n" +
            "                  \"parentId\": \"682554293965959168\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0001\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"480*900\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0701010001\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682554497079324672\",\n" +
            "                  \"name\": \"0701010002-DD\",\n" +
            "                  \"parentId\": \"682554293965959168\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0002\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"530*350\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0701010002\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682554691795693568\",\n" +
            "                  \"name\": \"0701010003-DD\",\n" +
            "                  \"parentId\": \"682554293965959168\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0003\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"280*350\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0701010003\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"682554775182651392\",\n" +
            "                  \"name\": \"0701010004-DD\",\n" +
            "                  \"parentId\": \"682554293965959168\",\n" +
            "                  \"level\": 4,\n" +
            "                  \"children\": null,\n" +
            "                  \"leaf\": true,\n" +
            "                  \"code\": \"0004\",\n" +
            "                  \"codeName\": \"\",\n" +
            "                  \"strength\": \"800*900\",\n" +
            "                  \"packageSize\": \"\",\n" +
            "                  \"unit\": \"kg\",\n" +
            "                  \"fullCode\": \"0701010004\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"leaf\": false,\n" +
            "              \"code\": \"01\",\n" +
            "              \"codeName\": null,\n" +
            "              \"strength\": null,\n" +
            "              \"packageSize\": null,\n" +
            "              \"unit\": null,\n" +
            "              \"fullCode\": \"070101\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"leaf\": false,\n" +
            "          \"code\": \"01\",\n" +
            "          \"codeName\": null,\n" +
            "          \"strength\": null,\n" +
            "          \"packageSize\": null,\n" +
            "          \"unit\": null,\n" +
            "          \"fullCode\": \"0701\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"leaf\": false,\n" +
            "      \"code\": \"07\",\n" +
            "      \"codeName\": null,\n" +
            "      \"strength\": null,\n" +
            "      \"packageSize\": null,\n" +
            "      \"unit\": null,\n" +
            "      \"fullCode\": \"07\"\n" +
            "    }\n" +
            "  ]";
}
