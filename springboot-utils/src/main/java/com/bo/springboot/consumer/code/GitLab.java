package com.bo.springboot.consumer.code;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @auther bo
 * @Date 2021/6/22 17:16
 * @description
 */
public class GitLab {
    public static Boolean connectGitlabWithTimeout(String url, String token) throws GitLabApiException {
        // Create a GitLabApi instance to communicate with your GitLab server
        GitLabApi gitLabApi = new GitLabApi(url, token);
        // Get the list of projects your account has access to
        gitLabApi.getVersion();
        return true;
    }

    public static boolean test() throws Exception {
        ThrowingFunction.wrap(() -> connectGitlabWithTimeout("http://www.baidu.com", "fagagg"));
        ThrowingFunction.wrapWithoutException(() -> connectGitlabWithTimeout("http://www.baidu.com", "fagagg"));
        return true;
    }

    public static boolean test(ThrowingFunction throwingFunction) throws Exception {
        ThrowingFunction.wrap(() -> connectGitlabWithTimeout("http://www.baidu.com", "fagagg"));
        Boolean fagagg = ThrowingFunction.wrapWithoutException(() -> connectGitlabWithTimeout("http://www.baidu.com", "fagagg"));
        return fagagg;
    }

    public static void main(String[] args) throws Exception {
        Boolean bo = true;
        System.out.println(bo.toString());
    }
}
