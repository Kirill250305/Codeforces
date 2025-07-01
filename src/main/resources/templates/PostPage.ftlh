<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>
        <#if comment??>
            <div class="form-comment">
                <form method="post" action="">
                    <input type="hidden" name="action" value="writeComment"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Comment</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Comment">
                    </div>
                </form>
            </div>
        </#if>
        <#if post.comments?size != 0>
            <div class="comments">Comments</div>
            <@c.comments post=post/>
        <#else>
            <div class="comments">No such comments</div>
        </#if>
    <#else>
        <tr class="noData">
            <td colspan="3">
                No such post
            </td>
        </tr>
    </#if>
</@c.page>
