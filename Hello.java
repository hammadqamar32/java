import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class GitOperations {

    private static final String LOCAL_REPO_PATH = "path/to/your/local/repo";
    private static final String REMOTE_REPO_URL = "https://github.com/yourusername/your-repo.git";
    private static final String USERNAME = "your-username";
    private static final String PASSWORD = "your-password";

    public static void main(String[] args) {
        try (Git git = Git.open(new File(LOCAL_REPO_PATH))) {
            commitChanges(git, "Commit message here");
            pushChanges(git);
            pullChanges(git);
            mergeBranch(git, "feature-branch");
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

    // Commit changes
    public static void commitChanges(Git git, String message) throws GitAPIException {
        git.add().addFilepattern(".").call();
        git.commit().setMessage(message).call();
        System.out.println("Changes committed with message: " + message);
    }

    // Push changes to remote repository
    public static void pushChanges(Git git) throws GitAPIException {
        git.push()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD))
           .call();
        System.out.println("Changes pushed to remote repository");
    }

    // Pull changes from remote repository
    public static void pullChanges(Git git) throws GitAPIException {
        PullResult result = git.pull()
                               .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD))
                               .call();
        if (result.isSuccessful()) {
            System.out.println("Pull successful");
        } else {
            System.out.println("Pull encountered issues");
        }
    }

    // Merge a branch into the current branch
    public static void mergeBranch(Git git, String branchName) throws GitAPIException {
        Ref branchRef = git.getRepository().findRef(branchName);
        git.merge().include(branchRef).call();
        System.out.println("Merged branch: " + branchName);
    }
}
