package Ywk.Data.Keyword;

import Ywk.Api.ApiInstance;
import Ywk.Api.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KeywordGenerator implements ApiInstance {
    private static final Logger logger = LoggerFactory.getLogger(KeywordGenerator.class);
    private static KeywordGenerator instance;
    private ApiStatus initStatus = ApiStatus.WAITING;

    private MixType type = MixType.PREFIX_MAIN_SUFFIX;

    private final List<IGenerator> generators = new ArrayList<>();

    private final Map<WordType, String[]> words = new HashMap<>();

    private int maxRun = 0;
    private int curRun = 0;

    private Iterator<IGenerator> iterator = null;
    private IGenerator generator = null;


    private KeywordGenerator() {


    }

    public static KeywordGenerator getInstance() {
        if (instance == null) {
            instance = new KeywordGenerator();
        }
        return instance;
    }

    public void setMaxRun(int maxRun) {
        this.maxRun = maxRun;
    }

    public void resetGenerators() {
        iterator = generators.iterator();
        generator = iterator.next();
        curRun = 0;
        if (maxRun == 0) maxRun = getTotal();
        generators.forEach(IGenerator::clear);
    }

    public void initGenerators() {
        generators.clear();
        if (type != MixType.CUSTOM) {
            generators.add(new SingleGenerator(words.getOrDefault(WordType.Main, new String[]{})));
            generators.add(new SingleGenerator(words.getOrDefault(WordType.Title, new String[]{})));
            generators.add(new SingleGenerator(words.getOrDefault(WordType.QuestionTitle, new String[]{})));
        }

        switch (type) {
            case PREFIX_MAIN:
                generators.add(new DoubleCombineGenerator(words.getOrDefault(WordType.Prefix, new String[]{}), words.getOrDefault(WordType.Main, new String[]{})));
                break;
            case PREFIX_MAIN_SUFFIX:
                generators.add(new TripleCombineGenerator(words.getOrDefault(WordType.Prefix, new String[]{}), words.getOrDefault(WordType.Main, new String[]{}), words.getOrDefault(WordType.Suffix, new String[]{})));
                break;
            case MAIN_SUFFIX:
                generators.add(new DoubleCombineGenerator(words.getOrDefault(WordType.Main, new String[]{}), words.getOrDefault(WordType.Suffix, new String[]{})));
                break;
            case HEAD_MAIN:
                generators.add(new DoubleCombineGenerator(words.getOrDefault(WordType.Head, new String[]{}), words.getOrDefault(WordType.Main, new String[]{})));
                break;
            case HEAD_MAIN_SUFFIX:
                generators.add(new TripleCombineGenerator(words.getOrDefault(WordType.Head, new String[]{}), words.getOrDefault(WordType.Main, new String[]{}), words.getOrDefault(WordType.Suffix, new String[]{})));
                break;
            case PREFIX_HEAD_MAIN:
                generators.add(new TripleCombineGenerator(words.getOrDefault(WordType.Prefix, new String[]{}), words.getOrDefault(WordType.Head, new String[]{}), words.getOrDefault(WordType.Main, new String[]{})));
                break;
            case PREFIX_HEAD_MAIN_SUFFIX:
                generators.add(new QuadCombineGenerator(words.getOrDefault(WordType.Prefix, new String[]{})
                        , words.getOrDefault(WordType.Head, new String[]{})
                        , words.getOrDefault(WordType.Main, new String[]{})
                        , words.getOrDefault(WordType.Suffix, new String[]{})
                ));
                break;
            case CUSTOM:
                generators.add(new SingleGenerator(words.getOrDefault(WordType.Custom, new String[]{})));
        }
        logger.info("select type {}", type);


        resetGenerators();

    }

    private int calculateChunkNumber() {
        int total = getTotal();
        if (total > 100000) {
            return 2000;
        } else if (total > 10000) {
            return 1000;
        } else if (total > 1000) {
            return 500;
        } else {
            return 50;
        }
    }

    private synchronized String next() {
        while (!generator.hasNext() && iterator.hasNext()) {
            generator = iterator.next();
        }
        if (!generator.hasNext()) {
            System.out.println("keyword next finished");
            return null;
        }
        return generator.next();
    }

    public void setWord(WordType type, String[] arr) {
        if (arr.length == 0) {
            arr = new String[]{""};
        }
        words.put(type, arr);
    }

    public void setInitSuccess() {
        initStatus = ApiStatus.SUCCESS;
    }

    public synchronized String[] nextChunk() {
        int chunkNumber = calculateChunkNumber();
        int i = 0;
        List<String> list = new LinkedList<>();
        while (i <= chunkNumber && curRun < maxRun) {
            var str = next();
            if (str == null) {
                break;
            }
            list.add(str);
            i++;
            curRun++;
        }

        if (list.isEmpty()) return null;
        return list.toArray(new String[0]);

    }

    public void setType(MixType type) {
        this.type = type;
        initGenerators();
    }

    public int getTotal() {
        return generators.stream()
                .map(IGenerator::count)
                .reduce(0, Integer::sum);
    }


    @Override
    public ApiStatus inited() {
        return initStatus;
    }

    @Override
    public void initFailed() {
        initStatus = ApiStatus.FAILED;
    }

    public enum WordType {
        Main,
        Prefix,
        Suffix,
        Head,
        Title,
        QuestionTitle,
        Custom
    }

    public enum MixType {
        PREFIX_MAIN_SUFFIX,
        PREFIX_MAIN,
        MAIN,
        MAIN_SUFFIX,
        CUSTOM,
        HEAD_MAIN,
        PREFIX_HEAD_MAIN,
        HEAD_MAIN_SUFFIX,
        PREFIX_HEAD_MAIN_SUFFIX
    }

}
