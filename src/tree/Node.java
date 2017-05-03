package tree;

class Node< K extends Comparable, T extends IHasKey<K>  > {
    private Node left;
    private Node right;
    T data;

    @Override
    public String toString() {
        return data.toString();
    }

    Node(final T data) {
        left = right = null;
        this.data = data;
    }

    private static class SearchResult {
        private Node found;
        private Node parent;
        private SearchResult(final Node found, final Node parent) {
            this.found = found;
            this.parent = parent;
        }
    }

    private static SearchResult search(Node node, final Comparable key) {
        Node parent = null;
        while (node != null) {
            int compare_result = key.compareTo(node.data.getKey());
            if (compare_result == 0)
                break;
            parent = node;
            if (compare_result < 0)
                node = node.left;
            else
                node = node.right;
        }
        return new SearchResult(node, parent);
    }

    static Node find(final Node node, final Comparable key) {
        if (node == null) return null;
        SearchResult searchResult = search(node, key);
        return searchResult.found;
    }

    static Node next(final Node root, final Comparable key) {
        Node node = root, last_bigger = null;
        while (node != null) {
            int compare_result = node.data.getKey().compareTo(key);
            if (compare_result > 0) {
                last_bigger = node;
                node = node.left;
            } else
                node = node.right;
        }
        return last_bigger;
    }

    static void add(final Node root, final IHasKey<Comparable> data) {
        SearchResult searchResult = Node.search(root, data.getKey());
        if (searchResult.found != null) {
            throw new IllegalArgumentException("can't add same element");
        } else {
            Node found = searchResult.parent;
            if (data.getKey().compareTo(found.data.getKey()) < 0) {
                if (found.left != null) throw new InternalError("found.left != null");
                found.left = new Node(data);
            } else {
                if (found.right != null) throw new InternalError("found.right != null");
                found.right = new Node(data);
            }
        }

    }

    private static int childrenCount(final Node node) {
        return (node.left != null ? 1 : 0) + (node.right != null ? 1 : 0);
    }

    private static Node getChild(final Node node) {
        if (node.left != null)
            return node.left;
        else
            return node.right;
    }

    private static void disconnectFromParent(final Node node, final Node parent) {
        if (parent != null) {
            if (parent.left == node)
                parent.left = null;
            else if (parent.right == node)
                parent.right = null;
            else
                throw new InternalError("can't disconnect from parent");
        }
    }

    // root have at least 1 children
    static Node remove(final Node root, final Comparable key) {
        SearchResult searchResult = Node.search(root, key);
        if (searchResult.found == null)
            return null;
        switch (childrenCount(searchResult.found)) {
            case 0:
                disconnectFromParent(searchResult.found, searchResult.parent);
                break;
            case 1:
                Node child = getChild(searchResult.found);
                if (searchResult.parent == null)
                    return child;
                replaceChildWithNew(searchResult.parent, searchResult.found, child);
                break;
            case 2:
                SearchResult nextSearchResult = minimumSavingParent(searchResult.found.right, searchResult.found); //next(root, searchResult.found.data.key);
                Node next = nextSearchResult.found;
                {
                    // removing next:
                    replaceChildWithNew(nextSearchResult.parent, next, next.right);
                }
                next.left = searchResult.found.left;
                next.right = searchResult.found.right;
                if (searchResult.parent == null)
                    return next;
                replaceChildWithNew(searchResult.parent, searchResult.found, next);
                break;
            default:
                throw new InternalError("unknown children count");
        }
        return root;
    }

    private static SearchResult minimumSavingParent(final Node node, final Node parent) {
        Node current = node, cur_parent = parent;
        while (current.left != null) {
            cur_parent = current;
            current = current.left;
        }
        return new SearchResult(current, cur_parent);
    }

    static Node minimum(final Node node) {
        if (node == null)
            return null;
        return minimumSavingParent(node, null).found;
    }

    private static void replaceChildWithNew(final Node parent, final Node old_child, final Node new_child) {
        if (parent.left == old_child)
            parent.left = new_child;
        else if (parent.right == old_child)
            parent.right = new_child;
        else
            throw new InternalError("there is no such old_child in parent");
    }
}
