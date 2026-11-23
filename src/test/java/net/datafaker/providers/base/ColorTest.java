package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class ColorTest extends BaseFakerTest {

    private final Color color = faker.color();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(color::name, "color.name", "(\\w+ ?){1,2}"));
    }

    @ParameterizedTest
    @CsvSource({"zh-CN, 红色, 蓝色", "zh-TW, 紅色, 藍色"})
    void chineseColorNames(String languageTag, String red, String blue) {
        BaseFaker chineseFaker = new BaseFaker(Locale.forLanguageTag(languageTag));
        testProviderList(TestSpec.of(chineseFaker.color()::name, "color.name", "\\p{IsHan}+"), chineseFaker);
        assertThat(getBaseList(chineseFaker, "color.name")).contains(red, blue);
    }

    @Test
    void testHex() {
        assertThat(color.hex()).matches("^#[0-9A-F]{6}$");
    }

    @Test
    void testHexNoHashSign() {
        assertThat(color.hex(false)).matches("^[0-9A-F]{6}$");
    }
}
